package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dao.cache.RedisDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnums;
import com.seckill.exception.RepeatException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
import com.seckill.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by 朱文赵
 * 2017/10/7
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    /**
     * 注入redisDao 实现缓存
     */
    @Autowired
    private RedisDao redisDao;

    /**
     * 查询所得秒杀记录
     *
     * @return
     */
    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    /**
     * 通过id查找一个Seckill
     *
     * @param seckillId 商品id
     * @return
     */
    @Override
    public Seckill getById(Long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 秒杀开启时 输出秒杀接口， 否侧输出系统时间秒杀时间
     * 优化缓存机制
     *
     * @param seckillId 商品id
     * @return exposer DTO
     */
    @Override
    public Exposer exportSeckillUrl(Long seckillId) {
        //缓存优化：redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            //从数据库中获取
            seckill = getById(seckillId);
            //数据库中都没有那就直接返回false
            if (seckill == null) {
                return new Exposer(false, seckillId);
            }
            //添加到缓存中
            redisDao.putSeckill(seckill);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //获得系统时间
        Date currentTime = new Date();
        if (currentTime.getTime() < startTime.getTime() ||
                endTime.getTime() < currentTime.getTime()) {
            return new Exposer(false, seckillId,
                    currentTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = MD5Util.getMd5(seckillId);
        //返回秒杀已经开启
        return new Exposer(true, md5, seckillId);
    }

    /**
     * 执行秒杀操作
     *
     * @param seckillId 商品id
     * @param userPhone 用户手机号
     * @param md5       用户地址
     * @throws SeckillException 使用声明式事务的优点
     *                          1.开发团队的达成一致约定，明确标注事务方法的编程风格
     *                          2.保证事务方法的执行时间要尽可能短， 同时不要穿插网络操作 比如http和tcp，如果非要的话可以剥离到事务方法之外
     *                          3.不是所得方法都需要事务，如只有一条写入操作或者只读
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5)
            throws SeckillException {
        if (md5 == null || !md5.equals(MD5Util.getMd5(seckillId))) {
            throw new SeckillException("seckill data rewite");
        }
        //执行秒杀业务逻辑 减库存 记录用户秒杀行为
        Date currentTime = new Date();
        try {
            /*优化：改变业务逻辑， 先执行insert 在执行update操作*/

            //记录购买行为
            Integer insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                throw new RepeatException("seckill repeat");
            } else {
                //减库存
                Integer updateCount = seckillDao.reduceNumber(seckillId, currentTime);
                if (updateCount <= 0) {
                    //没有更新记录 rollback
                    throw new SeckillCloseException("Seckill is closed");
                } else {
                    //commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSekcill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnums.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException | RepeatException e) {
            throw e;
        } catch (Exception e) {
            log.error("未知异常， e={}", e);
            //所有的异常
            throw new SeckillException("seckill inner error：" + e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillByProcedure(Long seckillId, Long userPhone, String md5)
            throws SeckillException {
        return null;
    }
}

package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
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

import java.util.Date;
import java.util.List;

/**
 * Created by 朱文赵
 * 2017/10/7
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService{

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

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
     * 秒杀开启式 输出秒杀接口， 否侧输出系统时间秒杀时间
     *
     * @param seckillId 商品id
     * @return exposer DTO
     */
    @Override
    public Exposer exportSeckillUrl(Long seckillId) {
        Seckill seckill = getById(seckillId);
        if(seckill == null){
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //获得系统时间
        Date currentTime = new Date();
        if(currentTime.getTime() < startTime.getTime() ||
                endTime.getTime() < currentTime.getTime()){
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
     * @throws SeckillException
     */
    @Override
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws SeckillException {
        if(md5 == null || ! md5.equals(MD5Util.getMd5(seckillId))){
            throw new SeckillException("seckill data rewite");
        }
        //执行秒杀业务逻辑 减库存 记录用户秒杀行为
        Date currentTime = new Date();
        try {
            //减库存
            Integer updateCount = seckillDao.reduceNumber(seckillId, currentTime);
            if (updateCount <= 0){
                //没有更新记录
                throw new SeckillCloseException("Seckill is closed");
            }else{
                //记录购买行为
                Integer insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if(insertCount <= 0){
                    throw new RepeatException("seckill repeat");
                }else{
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSekcill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnums.SUCCESS, successKilled);
                }
            }
        } catch(SeckillCloseException | RepeatException e){
            throw e;
        } catch (Exception e) {
            log.error("未知异常， e={}", e);
            //所有的异常
            throw new SeckillException("seckill inner error："+e.getMessage());
        }
    }
}

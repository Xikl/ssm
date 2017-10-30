package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口 ： 站在使用者的角度
 * 三个方面： 方法定义粒度 参数 返回类型
 * Created by 朱文赵
 * 2017/10/7
 */
public interface SeckillService {

    /**
     * 查询所得秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 通过id查找一个Seckill
     * @param seckillId 商品id
     * @return
     */
    Seckill getById(Long seckillId);

    /**
     * 秒杀开启式 输出秒杀接口， 否侧输出系统时间秒杀时间
     * @param seckillId 商品id
     * @return exposer DTO
     */
    Exposer exportSeckillUrl(Long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId 商品id
     * @param userPhone 用户手机号
     * @param md5 用户地址
     * @throws SeckillException
     */
    SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5)
        throws SeckillException;

    /**
     * 通过存储过程执行秒杀操作
     * @param seckillId 商品id
     * @param userPhone 用户手机号
     * @param md5 用户地址
     * @throws SeckillException
     */
    SeckillExecution executeSeckillByProcedure(Long seckillId, Long userPhone, String md5)
            throws SeckillException;


}

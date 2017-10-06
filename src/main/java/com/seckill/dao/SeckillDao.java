package com.seckill.dao;

import com.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * seckilldao层
 * Created by 朱文赵
 * 2017/10/6
 */
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 表示影响的行数 0 不成功 ， >= 1 正常
     */
    Integer reduceNumber(Long seckillId, Date killTime);

    /**
     * 根据id查库存
     * @param SeckillId
     * @return
     */
    Seckill queryById(Long SeckillId);

    /**
     * 根据偏移量查询商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(Integer offset, Integer limit);



}

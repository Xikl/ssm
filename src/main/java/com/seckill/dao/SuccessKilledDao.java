package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * SuccessKilledDao
 * Created by 朱文赵
 * 2017/10/6
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的结果集数量
     */
    Integer insertSuccessKilled(@Param("seckillId") Long seckillId, @Param("userPhone") Long userPhone);

    /**
     * 根据id查询SuccessKilled并且携带Seckill的属性
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSekcill(@Param("seckillId") Long seckillId,@Param("userPhone") Long userPhone);


}

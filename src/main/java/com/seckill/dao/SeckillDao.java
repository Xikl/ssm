package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    Integer reduceNumber(@Param("seckillId") Long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查库存
     * @param SeckillId
     * @return
     */
    Seckill queryById(Long SeckillId);

    /**
     * 根据偏移量查询商品列表
     * @param offset
     * @param limit 加入@Param注解 告诉mybatis来传入该形参
     * @return
     */
    List<Seckill> queryAll(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 使用存储过程进行秒杀
     * @param paramMap
     */
    void killByProcedure(Map<String, Object> paramMap);

}

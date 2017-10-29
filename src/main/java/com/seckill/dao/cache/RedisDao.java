package com.seckill.dao.cache;

import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.entity.Seckill;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by 朱文赵
 * 2017/10/29
 * 缓存操作
 */
@Slf4j
public class RedisDao {

    private JedisPool jedisPool;

    /** 通过反射机制来实现*/
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    /**
     * 获得缓存
     *
     * @param seckillId
     * @return
     */
    public Seckill getSeckill(Long seckillId) {
        //redis操作逻辑
        Jedis jedis = jedisPool.getResource();
        String key = "seckill:"+seckillId;
        //并没有实现序列化操作
        //get-> byte[] -> 反序列化->(object)Seckill
        jedis.get(key.getBytes());

        jedis.close();
        return null;
    }

    /**
     * 添加缓存
     *
     * @param seckill
     * @return
     */
    public String putSeckill(Seckill seckill) {
        return null;
    }

}

package com.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
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

    /**
     * 通过反射机制来实现
     */
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
        try {
            //redis操作逻辑
            try (Jedis jedis = jedisPool.getResource()) {
                String key = "seckill:" + seckillId;
                //并没有实现序列化操作
                //get-> byte[] -> 反序列化->(object)Seckill
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //通过一个工具列来创建一个空对象
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    //seckill 序列化操作
                    return seckill;
                }
            }
        } catch (Exception e) {
            log.error("【序列化错误】message={}", e.getMessage());
        }
        return null;
    }

    /**
     * 添加缓存
     *
     * @param seckill
     * @return
     */
    public String putSeckill(Seckill seckill) {
        //序列化过程
        //set object(seckill) -> 序列化 -》 byte[]
        try {
            try (Jedis jedis = jedisPool.getResource()) {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.
                        toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时时间为60 * 60 秒
                int timeout = 60 * 60;
                return jedis.setex(key.getBytes(), timeout, bytes);
            }
        } catch (Exception e) {
            log.error("【序列化错误】message={}", e.getMessage());
        }
        return null;
    }

}

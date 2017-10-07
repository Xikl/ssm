package com.seckill.exception;

/**
 * 秒杀相关异常
 * Created by 朱文赵
 * 2017/10/7
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}

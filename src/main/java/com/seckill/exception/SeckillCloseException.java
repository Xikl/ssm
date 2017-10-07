package com.seckill.exception;

/**
 * 秒杀关闭异常
 * Created by 朱文赵
 * 2017/10/7
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}

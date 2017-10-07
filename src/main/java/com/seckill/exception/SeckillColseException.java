package com.seckill.exception;

/**
 * 秒杀关闭异常
 * Created by 朱文赵
 * 2017/10/7
 */
public class SeckillColseException extends SeckillException {

    public SeckillColseException(String message) {
        super(message);
    }

    public SeckillColseException(String message, Throwable cause) {
        super(message, cause);
    }
}

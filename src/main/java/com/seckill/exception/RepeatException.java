package com.seckill.exception;

/**
 * 重复秒杀异常
 * Created by 朱文赵
 * 2017/10/7
 */
public class RepeatException extends  SeckillException{

    public RepeatException(String message) {
        super(message);
    }

    public RepeatException(String message, Throwable cause) {
        super(message, cause);
    }
}
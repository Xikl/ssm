package com.seckill.exception;

/**
 * 重复秒杀异常
 * Created by 朱文赵
 * 2017/10/7
 */
public class RepeaException extends  SeckillException{

    public RepeaException(String message) {
        super(message);
    }

    public RepeaException(String message, Throwable cause) {
        super(message, cause);
    }
}

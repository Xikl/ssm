package com.seckill.vo;

import lombok.Data;

/**
 * Created by 朱文赵
 * 2017/10/9
 * 处理所有的ajax请求的返回类型， 封装json数据
 */
@Data
public class SeckillResult<T> {

    private boolean success;

    private T data;

    private String error;

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}

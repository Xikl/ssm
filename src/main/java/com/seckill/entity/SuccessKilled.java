package com.seckill.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by 朱文赵
 * 2017/10/6
 */
@Data
public class SuccessKilled {

    /**  id 非自增*/
    private Long seckillId;

    /** 用户手机号*/
    private Long userPhone;

    /**状态： -1 无效， 0 成功， 1 已付款， 2 已发货*/
    private Integer state;

    /** 创建时间*/
    private Date createTime;

    /** 修改时间*/
    private Date updateTime;

    /** 多对一的映射*/
    private Seckill seckill;
}

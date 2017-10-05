package com.seckill.entity;

import lombok.Data;

import java.util.Date;

/**
 * 秒杀表
 * Created by 朱文赵
 * 2017/10/6
 */
@Data
public class Seckill {

    /** 秒杀id*/
    private Long seckillId;

    /** 商品名称*/
    private String name;

    /** 库存*/
    private Integer number;

    /** 秒杀开始时间*/
    private Date startTime;

    /** 秒杀结束时间*/
    private Date endTime;

    /** 该字段创建时间*/
    private Date createTime;

    /** 该字段修改时间*/
    private Date updateTime;

}

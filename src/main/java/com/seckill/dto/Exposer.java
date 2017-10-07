package com.seckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据传输对象
 * 暴露秒杀地址dto
 * Created by 朱文赵
 * 2017/10/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exposer {

    /** 是否暴露*/
    private boolean exposed;

    /** 加密地址*/
    private String md5;

    /** 秒杀商品id*/
    private Long seckillId;

    /** 系统当前时间（ms）*/
    private Long now;

    /** 秒杀开始时间（ms）*/
    private Long start;

    /** 秒杀结束时间（ms）*/
    private Long end;

    public Exposer(boolean exposed, String md5, Long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, Long seckillId, Long now, Long start, Long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, Long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}

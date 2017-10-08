package com.seckill.dto;

import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnums;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装秒杀后执行后的结果
 * Created by 朱文赵
 * 2017/10/7
 */
@Data
@NoArgsConstructor
public class SeckillExecution {

    /** 秒杀商品id*/
    private Long seckillId;

    /** 秒杀结果*/
    private Integer state;

    /** 结果信息*/
    private String stateInfo;

    /** 秒杀成功对象*/
    private SuccessKilled successKilled;

    public SeckillExecution(Long seckillId, SeckillStateEnums seckillStateEnums) {
        this.seckillId = seckillId;
        this.state = seckillStateEnums.getState();
        this.stateInfo = seckillStateEnums.getStateInfo();
    }

    public SeckillExecution(Long seckillId, SeckillStateEnums seckillStateEnums, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStateEnums.getState();
        this.stateInfo = seckillStateEnums.getStateInfo();
        this.successKilled = successKilled;
    }
}

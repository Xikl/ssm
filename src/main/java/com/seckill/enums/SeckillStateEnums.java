package com.seckill.enums;

import lombok.Getter;

/**
 * Created by 朱文赵
 * 2017/10/8
 */
@Getter
public enum SeckillStateEnums {

    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITES(-3, "数据篡改"),

    ;

    private Integer state;
    private String stateInfo;

    SeckillStateEnums(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static SeckillStateEnums stateOf(Integer index){
        for (SeckillStateEnums state : values()){
            if(state.getState().equals(index)){
                return state;
            }
        }
        return null;
    }

}

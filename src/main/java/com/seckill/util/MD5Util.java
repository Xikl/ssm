package com.seckill.util;

import org.springframework.util.DigestUtils;

/**
 * Created by 朱文赵
 * 2017/10/7
 */
public class MD5Util {

    /** 给密码加点盐*/
    private static final String SLAT = "njonNUHPOJPOI**jasd90uj4“”23906amskd[m0o-0o2;'...a.w,ptwl+*&……&Hn";

    /**
     * 加密算法
     * @param seckillId
     */
    public static String getMd5(Long seckillId){
        String base = seckillId + "/" +SLAT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }


}

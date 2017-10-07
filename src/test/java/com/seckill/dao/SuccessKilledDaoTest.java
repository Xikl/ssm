package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 朱文赵
 * 2017/10/7
 */
@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:spring/spring.dao.xml"})
@Slf4j
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    /**
     * 测试第一次通过
     * 第二次不通过 原因是 双重主键约束 不循序有重复的主键！
     * @throws Exception
     */
    @Test
    public void insertSuccessKilled() throws Exception {
        Long id = 1000L;
        Long phone = 15088664612L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount: "+insertCount);
        assertEquals(1, insertCount);
    }

    @Test
    public void queryByIdWithSekcill() throws Exception {
        Long id = 1000L;
        Long phone = 15088664612L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSekcill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
        /*
        SuccessKilled(seckillId=1000, uerPhone=null, state=-1, createTime=Sat Oct 07 11:42:17 CST 2017, updateTime=null, seckill=Seckill(seckillId=1000, name=1000元秒杀iphone8, number=97, startTime=Sat Oct 07 11:03:36 CST 2017, endTime=Sun Oct 08 00:00:00 CST 2017, createTime=Thu Oct 05 23:48:45 CST 2017, updateTime=null))
        Seckill(seckillId=1000, name=1000元秒杀iphone8, number=97, startTime=Sat Oct 07 11:03:36 CST 2017, endTime=Sun Oct 08 00:00:00 CST 2017, createTime=Thu Oct 05 23:48:45 CST 2017, updateTime=null)
        */
    }

}
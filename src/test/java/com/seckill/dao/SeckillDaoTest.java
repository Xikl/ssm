package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by 朱文赵
 * 2017/10/7
 */
@RunWith(SpringRunner.class)
@Rollback
@ContextConfiguration({"classpath:spring/spring.dao.xml"})
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        /*
        1000元秒杀iphone8
        Seckill(seckillId=1000,
        name=1000元秒杀iphone8, number=100,
        startTime=Thu Oct 05 00:00:00 CST 2017,
        endTime=Fri Oct 06 00:00:00 CST 2017,
        createTime=Thu Oct 05 23:48:45 CST 2017,
        updateTime=Thu Oct 05 23:48:45 CST 2017)
        * */

    }

    /*
    错误信息
    org.mybatis.spring.MyBatisSystemException:
    nested exception is org.apache.ibatis.binding.BindingException:
    Parameter 'offset' not found. Available parameters are [arg1, arg0, param1, param2]
    解决办法： 在接口中 添加@Param注解
     */
    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckillList = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckillList){
            System.out.println(seckill);
        }
        /*
        Seckill(seckillId=1000, name=1000元秒杀iphone8, number=100, startTime=Thu Oct 05 00:00:00 CST 2017, endTime=Fri Oct 06 00:00:00 CST 2017, createTime=Thu Oct 05 23:48:45 CST 2017, updateTime=Thu Oct 05 23:48:45 CST 2017)
        Seckill(seckillId=1001, name=500元秒杀iPad2, number=200, startTime=Thu Oct 05 00:00:00 CST 2017, endTime=Fri Oct 06 00:00:00 CST 2017, createTime=Thu Oct 05 23:48:45 CST 2017, updateTime=Thu Oct 05 23:48:45 CST 2017)
        Seckill(seckillId=1002, name=300元秒杀小米5x, number=400, startTime=Thu Oct 05 00:00:00 CST 2017, endTime=Fri Oct 06 00:00:00 CST 2017, createTime=Thu Oct 05 23:48:45 CST 2017, updateTime=Thu Oct 05 23:48:45 CST 2017)
        Seckill(seckillId=1003, name=1元秒杀SpringCLoud全套课程, number=500, startTime=Thu Oct 05 00:00:00 CST 2017, endTime=Fri Oct 06 00:00:00 CST 2017, createTime=Thu Oct 05 23:48:45 CST 2017, updateTime=Thu Oct 05 23:48:45 CST 2017)

         */
    }

    @Test
    public void reduceNumber() throws Exception {
        Date date = new Date();
        int updateNumber = seckillDao.reduceNumber(1000L, date);
        System.out.println(updateNumber);
        assertEquals(1, updateNumber);
    }

}
package com.seckill.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 朱文赵
 * 2017/10/8
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring/*")
@Slf4j
public class SeckillServiceTest {

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
    }

    @Test
    public void getById() throws Exception {
    }

    @Test
    public void exportSeckillUrl() throws Exception {
    }

    @Test
    public void executeSeckill() throws Exception {
    }

}
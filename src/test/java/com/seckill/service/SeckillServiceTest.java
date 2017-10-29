package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        List<Seckill> seckillList = seckillService.getSeckillList();
        log.info("seckillList={}", seckillList);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        log.info("seckill={}", seckill);

    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        log.info("exposer={}", exposer);
//        /** 成功*/
        //Exposer(exposed=true,
        // md5=6584285067da07449f644af1fe6e7626,
        // seckillId=1000,
        // now=null, start=null, end=null)
//        失败
        //Exposer(exposed=false, md5=null, seckillId=1001,
        // now=1507451696985, start=1507132800000, end=1507219200000)
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000;
        long phone = 15088654692L;
        String md5 = "6584285067da07449f644af1fe6e7626";
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
        log.info("seckillExecution={}", seckillExecution);
    }

    /**
     * 将上述两个方法整合
     * 一起进行测试
     */
    @Test
    public void testSeckillLogic() throws Exception{
        long id = 1003L;
        long phone = 15089654692L;
        //暴露秒杀接口 主要靠时间来判定
        Exposer exposer = seckillService.exportSeckillUrl(id);
        log.info("exposer={}", exposer);
        if(exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            log.info("seckillExecution={}", seckillExecution);
        }else{
            log.warn("秒杀未开启 exposer={}", exposer);
        }

    }


}
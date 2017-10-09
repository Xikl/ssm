package com.seckill.controller;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.enums.SeckillStateEnums;
import com.seckill.exception.RepeatException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.vo.SeckillResult;
import com.seckill.entity.Seckill;
import com.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by 朱文赵
 * 2017/10/9
 */
@Controller
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list";
    }

    @GetMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            //重定向到list的请求
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @PostMapping(value = "/{seckillId}/exposer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<>(true, exposer);
        } catch (Exception e) {
            log.error("【暴露接口】暴露接口发生错误 , message={}", e.getMessage());
            result = new SeckillResult<>(false, e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/{seckillId}/{md5}/execution", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long killPhone) {
        if (killPhone == null) {
            return new SeckillResult<>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, killPhone, md5);
            result = new SeckillResult<>(true, seckillExecution);
        } catch (SeckillCloseException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnums.END);
            return new SeckillResult<>(false, seckillExecution);
        } catch (RepeatException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnums.REPEAT_KILL);
            return new SeckillResult<>(false, seckillExecution);
        } catch (SeckillException e) {
            log.error("【执行秒杀】 执行秒杀错误， message={}", e.getMessage());
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnums.INNER_ERROR);
            result = new SeckillResult<>(false, seckillExecution);
        }
        return result;
    }
}

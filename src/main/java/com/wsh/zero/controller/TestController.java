package com.wsh.zero.controller;

import com.wsh.util.*;
import com.wsh.util.mail.JavaMailUtil;
import com.wsh.util.mail.MailBean;
import com.wsh.util.rly.RlySMS;
import com.wsh.zero.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试")
@RestController
public class TestController {
    @Autowired
    SysUserService sysUserService;

    @PostMapping(value = "test")
    public ResultUtil upload() {
        RedisUtil redisUtil = SpringContextUtils.getBean("redisUtil", RedisUtil.class);
        redisUtil.hset("zero", "221", 2222);
        redisUtil.hset("zero1", "221", 2222);
        System.out.println(redisUtil.hget("zero", "222"));
        return ResultUtil.success("设置值成功");

    }

    @RequestMapping(value = "mail")
    public ResultUtil upload(@Validated MailBean mailBean) {
        return JavaMailUtil.sendMail(mailBean);
    }

    @RequestMapping(value = "rly/sms")
    public ResultUtil rlySendSms(String phone) {
        String vCode = Utils.getNonceStr(6);
        RedisUtil redisUtil = SpringContextUtils.getBean("redisUtil", RedisUtil.class);
        Object hget = redisUtil.hget(Consot.RLY, Consot.RLY + phone);
        if (null != hget) {
            return ResultUtil.failed(1, "短信发送频率过快，请一分钟后再试");
        }
        //设置过期时间5分钟
        redisUtil.hset(Consot.RLY, Consot.RLY + phone, vCode, 5 * 60);
        return RlySMS.rlySendSms(phone, "1", new String[]{vCode, "5"});
    }
}

package com.wsh.zero.controller;

import com.wsh.util.RedisUtil;
import com.wsh.util.ResultUtil;
import com.wsh.util.SpringContextUtils;
import com.wsh.util.mail.JavaMailUtil;
import com.wsh.util.mail.MailBean;
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
}

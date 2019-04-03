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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.wsh.util.Consot.*;

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

    @PostMapping(value = "rly/sms")
    public ResultUtil rlySendSms(@RequestParam String phone) {
        /**
         * 获取验证码的次数限制
         */
        String vCode = Utils.getNonceStr(6);
        RedisUtil redisUtil = SpringContextUtils.getBean("redisUtil", RedisUtil.class);
        Object hget = redisUtil.hget(GET_LIMIT, GET_LIMIT + phone);
        if (null == hget) {
            //计算当前时间到00:00:01相差多少秒
            redisUtil.hset(GET_LIMIT, GET_LIMIT + phone, 0, DateTimeUtil.differSecond());
        } else {
            int hgetInt = (int) hget;
            if (hgetInt >= SMS_LIMIT) {
                return ResultUtil.failed(1, "短信次数发送过多,请明天再试!!");
            }
            redisUtil.hset(GET_LIMIT, GET_LIMIT + phone, ++hgetInt);
        }
        //删除以前生成的验证码
        redisUtil.hdel(RLY, RLY + phone);
        //设置过期时间5分钟
        redisUtil.hset(RLY, RLY + phone, vCode, 5 * 60);
        System.out.println(String.format("%s vCode= %s", phone, vCode));
        return ResultUtil.success("OK");
//        return RlySMS.rlySendSms(phone, "1", new String[]{vCode, "5"});
    }

    @PostMapping(value = "check/sms")
    public ResultUtil checkSms(@RequestParam String phone, @RequestParam Object verificationCode) {
        RedisUtil redisUtil = SpringContextUtils.getBean("redisUtil", RedisUtil.class);
        Object hget = redisUtil.hget(RLY, RLY + phone);
        if (verificationCode.equals(hget)) {
            //删除以前生成的验证码
            redisUtil.hdel(RLY, RLY + phone);
            return ResultUtil.success("OK");
        }
        return ResultUtil.failed(1, "验证码不正确");
    }
}

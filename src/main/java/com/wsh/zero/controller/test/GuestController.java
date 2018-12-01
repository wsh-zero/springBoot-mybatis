package com.wsh.zero.controller.test;

import com.wsh.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//游客
@RestController
@RequestMapping("/guest")
public class GuestController {

    @RequestMapping(value = "/enter")
    public ResultUtil login() {
        return new ResultUtil<>("欢迎进入，您的身份是游客");
    }

    @RequestMapping(value = "/getMessage")
    public ResultUtil submitLogin() {

        return new ResultUtil<>("您拥有获得该接口的信息的权限!");
    }
}

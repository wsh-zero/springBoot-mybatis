package com.wsh.zero.controller.test;

import com.wsh.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//游客
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/getMessage")
    public ResultUtil submitLogin() {

        return new ResultUtil<>("您拥有用户权限，可以获得该接口的信息！!");
    }
}

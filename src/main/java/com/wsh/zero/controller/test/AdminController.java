package com.wsh.zero.controller.test;

import com.wsh.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//游客
@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/getMessage")
    public ResultUtil submitLogin() {

        return new ResultUtil<>("您拥有管理员权限，可以获得该接口的信息！");
    }
}

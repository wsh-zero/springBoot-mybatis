package com.wsh.zero.controller.test;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/")
public class LoginTestController {

    @RequestMapping("logout1")
    public String logout1() {
        return "logout";
    }

    @RequestMapping("login1")
    @RequiresAuthentication
    public String login() {
//        Subject subject = SecurityUtils.getSubject();
        return "login";
    }

    @RequestMapping("index1")
    public String index() {
        return "index1";
    }

    @RequestMapping("403")
    public String error() {
        return "403";
    }


}

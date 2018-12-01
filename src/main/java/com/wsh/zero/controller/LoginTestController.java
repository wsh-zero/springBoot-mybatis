package com.wsh.zero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginTestController {

    @RequestMapping("login1")
    public String login() {
        return "index";
    }

    @RequestMapping("index1")
    public String index() {
        return "index";
    }

    @RequestMapping("logout1")
    public String logout() {
        return "logout";
    }

    @RequestMapping("error1")
    public String error() {
        return "error";
    }


}

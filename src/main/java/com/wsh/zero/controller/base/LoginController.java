package com.wsh.zero.controller.base;

import com.wsh.util.ResultUtil;
import com.wsh.zero.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    SysUserService sysUserService;

    @RequestMapping(value = "/notLogin")
    public String notLogin() {
        return "index";
    }

    @RequestMapping(value = "/error1")
    public ResultUtil notRole() {
        return new ResultUtil<>(1, "您没有权限！");
    }

    /**
     * 登陆
     *
     * @param userName 用户名
     * @param userPwd  密码
     */


    @GetMapping("login")
    @ResponseBody
    public ResultUtil login(@RequestParam String userName, @RequestParam String userPwd) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        ResultUtil resultUtil = sysUserService.loginDataBaseCheck(userName, userPwd);
        int code = resultUtil.getCode();
        if (code > 0) {//数据库验证没通过
            return resultUtil;
        }
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(userName, userPwd);
        // 执行认证登陆
        subject.login(token);
        return new ResultUtil<>(0, "登录成功！");
    }
}

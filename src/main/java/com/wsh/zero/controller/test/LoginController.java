package com.wsh.zero.controller.test;

import com.wsh.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/notLogin")
    public ResultUtil notLogin() {
        return new ResultUtil<>("您尚未登陆！");
    }

    @RequestMapping(value = "/notRole")
    public ResultUtil notRole() {
        return new ResultUtil<>("您没有权限！");
    }

    @RequestMapping(value = "/logout")
    public ResultUtil logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return new ResultUtil<>("成功注销！");
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     */
    @RequestMapping(value = "/login")
    public ResultUtil login(String username, String password) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        subject.login(token);
        //根据权限，指定返回数据
//        String role = userMapper.getRole(username);
//        if ("user".equals(role)) {
//            return new ResultUtil<>("欢迎登陆");
//        }
//        if ("admin".equals(role)) {
//            return new ResultUtil<>("欢迎来到管理员页面");
//        }
        return new ResultUtil<>("权限错误！");
    }
}

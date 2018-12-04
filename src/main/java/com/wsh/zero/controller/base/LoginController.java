package com.wsh.zero.controller.base;

import com.wsh.util.ResultUtil;
import com.wsh.zero.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    SysUserService sysUserService;

    @PostMapping(value = "/notLogin")
    public ResultUtil notLogin() {
        return new ResultUtil<>(1001, "您还没有登录！");
    }

    @PostMapping(value = "/400")
    public ResultUtil error400() {
        return new ResultUtil<>(HttpStatus.BAD_REQUEST.value(), "错误的请求地址！");
    }

    @PostMapping(value = "/401")
    public ResultUtil error401() {
        return new ResultUtil<>(HttpStatus.UNAUTHORIZED.value(), "你没有权限！");
    }

    @PostMapping(value = "/500")
    public ResultUtil error500() {
        return new ResultUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "访问异常！");
    }

    @PostMapping(value = "/404")
    public ResultUtil error404() {
        return new ResultUtil<>(HttpStatus.NOT_FOUND.value(), "访问的URL不存在！");
    }

    /**
     * 登陆
     *
     * @param userName 用户名
     * @param userPwd  密码
     */


    @PostMapping("login")
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

package com.wsh.zero.controller.base;

import com.wsh.util.ResultUtil;
import com.wsh.zero.controller.aop.anno.SysLogTag;
import com.wsh.zero.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    SysUserService sysUserService;

    @GetMapping(value = "/notLogin")
    public ResultUtil notLogin() {
        return ResultUtil.failed(1001, "您还没有登录！");
    }

    @GetMapping(value = "/400")
    public ResultUtil error400() {
        return ResultUtil.failed(HttpStatus.BAD_REQUEST.value(), "错误的请求地址！");
    }

    @GetMapping(value = "/401")
    public ResultUtil error401() {
        return ResultUtil.failed(HttpStatus.UNAUTHORIZED.value(), "你没有权限！");
    }

    @GetMapping(value = "/500")
    public ResultUtil error500() {
        return ResultUtil.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), "访问异常！");
    }

    @GetMapping(value = "/404")
    public ResultUtil error404() {
        return ResultUtil.failed(HttpStatus.NOT_FOUND.value(), "访问的URL不存在！");
    }

    /**
     * 登陆
     *
     * @param userAmount 登录账号
     * @param userPwd    密码
     */


    @GetMapping("login")
    @SysLogTag(value = "系统用户", operation = "登录系统")
    public ResultUtil login(@RequestParam String userAmount, @RequestParam String userPwd) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        ResultUtil resultUtil = sysUserService.loginDataBaseCheck(userAmount, userPwd);
        int code = resultUtil.getCode();
        if (code > 0) {//数据库验证没通过
            return resultUtil;
        }
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(userAmount, userPwd);
        // 执行认证登陆
        subject.login(token);
        Map<String, String> map = new LinkedHashMap<>();
        String sessionId = (String) subject.getSession().getId();
        map.put("access_token", sessionId);
        return ResultUtil.success("登录成功！", map);
    }
}

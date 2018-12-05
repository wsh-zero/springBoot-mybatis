package com.wsh.zero.controller.base;

import com.wsh.config.shiro.jwt.JWTUtil;
import com.wsh.util.ResultUtil;
import com.wsh.zero.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登陆
     *
     * @param userName 用户名
     * @param userPwd  密码
     */

    @GetMapping("login")
    public ResultUtil login(@RequestParam String userName, @RequestParam String userPwd) {
        ResultUtil resultUtil = sysUserService.loginDataBaseCheck(userName, userPwd);
        int code = resultUtil.getCode();
        if (code > 0) {//数据库验证没通过
            return resultUtil;
        }
        Map<String, String> map = new LinkedHashMap<>();
        map.put("access_token", JWTUtil.sign(userName, userPwd));
        return new ResultUtil<>(0, "登录成功！", map);
    }

    @GetMapping("/article")
    public ResultUtil article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResultUtil<>(200, "You are already logged in");
        } else {
            return new ResultUtil<>(200, "You are guest");
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResultUtil requireAuth() {
        return new ResultUtil<>(200, "You are authenticated");
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResultUtil requireRole() {
        return new ResultUtil<>(200, "You are visiting require_role");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResultUtil requirePermission() {
        return new ResultUtil<>(200, "You are visiting permission require edit,view");
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultUtil unauthorized() {
        return new ResultUtil<>(401, "Unauthorized");
    }
}

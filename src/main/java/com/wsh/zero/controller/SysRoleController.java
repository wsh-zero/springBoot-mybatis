package com.wsh.zero.controller;

import com.wsh.util.ResultUtil;
import com.wsh.zero.controller.base.BaseController;
import com.wsh.zero.entity.SysRoleEntity;
import com.wsh.zero.query.SysRoleQuery;
import com.wsh.zero.service.SysRoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "角色")
@RestController
@RequestMapping("/sys/role/")
public class SysRoleController extends BaseController<SysRoleService, SysRoleQuery, SysRoleEntity> {

    @Autowired
    SysRoleService sysRoleService;

    @GetMapping("get/roles")
    public ResultUtil getRoles() {
        return sysRoleService.getRoles();
    }

}

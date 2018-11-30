package com.wsh.zero.controller;

import com.wsh.zero.controller.base.BaseController;
import com.wsh.zero.entity.SysRoleEntity;
import com.wsh.zero.query.SysRoleQuery;
import com.wsh.zero.service.SysRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户")
@RestController
@RequestMapping("/sys/role/")
public class SysRoleController extends BaseController<SysRoleService, SysRoleQuery, SysRoleEntity> {

}

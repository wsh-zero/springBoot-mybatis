package com.zero.role.controller;

import com.zero.base.controller.BaseController;
import com.zero.role.entity.SysRoleEntity;
import com.zero.role.query.SysRoleQuery;
import com.zero.role.service.SysRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户")
@RestController
@RequestMapping("/sys/role/")
public class SysRoleController extends BaseController<SysRoleService, SysRoleQuery, SysRoleEntity> {

}

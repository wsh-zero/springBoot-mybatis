package com.zero.user.controller;

import com.zero.user.entity.SysUserEntity;
import com.zero.user.query.SysUserQuery;
import com.zero.user.service.SysUserService;
import com.zero.util.LayuiTableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户")
@RestController
@RequestMapping("/sys/user/")
public class SysUserController {
    @Autowired
    SysUserService service;

    @GetMapping("list")
    @ApiOperation(value = "列表", notes = "获取用户列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "limit", value = "显示多少行", required = true)})
    public LayuiTableUtil getSysUserList(SysUserQuery query, @RequestParam int page, @RequestParam int limit) {
        return service.getSysUserList(query, page, limit);
    }

    @PostMapping("save")
    @ApiOperation(value = "保存/修改", notes = "保存/修改用户信息")
    public void save(SysUserEntity entity) {
        service.save(entity);
    }

}

package com.wsh.zero.controller.base;

import com.wsh.util.ResultUtil;
import com.wsh.util.TableUtil;
import com.wsh.zero.service.base.BaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class BaseController<S extends BaseService, Q, E> {
    @Autowired
    private S baseService;

    @GetMapping("list")
    @ApiOperation(value = "列表", notes = "获取列表信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "limit", value = "显示多少行", required = true)})
    public TableUtil getList(Q q, @RequestParam int page, @RequestParam int limit) {
        return baseService.getList(q, page, limit);
    }

    @PostMapping("save")
    @ApiOperation(value = "保存/修改", notes = "保存/修改信息")
    public ResultUtil save(E e) {
        return baseService.save(e);
    }

    @RequestMapping("test")
    @RequiresRoles("zzzzz")
    public String test() {
        return "返回成功";
    }

}

package com.wsh.zero.controller.base;

import com.wsh.util.ResultUtil;
import com.wsh.util.TableUtil;
import com.wsh.zero.service.base.BaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class BaseController<S extends BaseService, Q, E> {
    @Autowired
    private S baseService;

    @GetMapping("list")
    @ApiOperation(value = "列表", notes = "获取列表信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "limit", value = "显示多少行", required = true)})
    public TableUtil getList(Q q, @RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit) {
        return baseService.getList(q, page, limit);
    }

    @PostMapping("save")
    @ApiOperation(value = "添加", notes = "添加信息")
    public ResultUtil save(E e) {
        return baseService.save(e);
    }

    @PostMapping("update")
    @ApiOperation(value = "修改", notes = "修改信息")
    public ResultUtil update(E e) {
        return baseService.update(e);
    }

    @PostMapping("del")
    @ApiOperation(value = "删除", notes = "删除信息")
    public ResultUtil del(String[] ids) {
        return baseService.del(ids);
    }

    @GetMapping("get")
    @ApiOperation(value = "获取一条", notes = "通过主键获取数据")
    public ResultUtil getByPrimaryKey(@RequestParam String id) {
        return baseService.getByPrimaryKey(id);
    }

}

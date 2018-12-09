package com.wsh.zero.controller;

import com.wsh.util.ResultUtil;
import com.wsh.zero.entity.SysMenuEntity;
import com.wsh.zero.service.SysMenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "菜单")
@RestController
@RequestMapping("/sys/menu/")
public class SysMenuController {
    @Autowired
    SysMenuService sysMenuService;

    @GetMapping("list")
    public ResultUtil list() {
        return sysMenuService.getMenuList();
    }

    @GetMapping("get")
    public ResultUtil getByPrimaryKey(@RequestParam String id) {
        return sysMenuService.getByPrimaryKey(id);
    }

    @PostMapping("move")
    public ResultUtil calculationLevel(@RequestParam String id, Integer direction) {
        return sysMenuService.calculationLevel(id, direction);
    }

    @PostMapping("save")
    public ResultUtil save(@Validated SysMenuEntity entity) {
        return sysMenuService.save(entity);
    }

    @PostMapping("get_tree")
    public ResultUtil getMenuTree() {
        return sysMenuService.getMenuTree();
    }
}

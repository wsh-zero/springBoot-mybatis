package com.wsh.zero.controller;

import com.wsh.util.ResultUtil;
import com.wsh.zero.entity.SysMenuEntity;
import com.wsh.zero.service.SysMenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("move")
    public ResultUtil calculationLevel(@RequestParam String id, @RequestParam Integer direction) {
        return sysMenuService.calculationLevel(id, direction);
    }

    @GetMapping("save")
    public ResultUtil save(@Validated SysMenuEntity entity) {
        return sysMenuService.save(entity);
    }

    @GetMapping("get_tree")
    public ResultUtil getMenuTree() {
        return sysMenuService.getMenuTree();
    }
}

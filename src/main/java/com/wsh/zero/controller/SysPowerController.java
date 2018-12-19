package com.wsh.zero.controller;

import com.wsh.util.ResultUtil;
import com.wsh.zero.controller.base.BaseController;
import com.wsh.zero.entity.SysPowerEntity;
import com.wsh.zero.query.SysPowerQuery;
import com.wsh.zero.service.SysPowerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "权限")
@RestController
@RequestMapping("/sys/power/")
public class SysPowerController extends BaseController<SysPowerService, SysPowerQuery, SysPowerEntity> {

    @Autowired
    SysPowerService sysPowerService;

    @GetMapping("get/powers")
    public ResultUtil getPowers() {
        return sysPowerService.getPowers();
    }

    @PostMapping("get_tree")
    public ResultUtil getPowerTree() {
        return sysPowerService.getPowerTree();
    }

}

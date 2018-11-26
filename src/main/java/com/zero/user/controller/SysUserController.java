package com.zero.user.controller;

import com.zero.user.entity.SysUserEntity;
import com.zero.user.query.SysUserQuery;
import com.zero.user.service.SysUserService;
import com.zero.util.LayuiTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/user/")
public class SysUserController {
    @Autowired
    SysUserService service;

    @RequestMapping("list")
    public LayuiTableUtil getSysUserList(SysUserQuery query, int page, int limit) {
        return service.getSysUserList(query, page, limit);
    }

    @RequestMapping("save")
    public void save(SysUserEntity entity) {
        service.save(entity);
    }

}

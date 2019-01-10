package com.wsh.zero.controller;

import com.wsh.util.ResultUtil;
import com.wsh.zero.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "测试")
@RestController
@RequestMapping("/test/")
public class TestController {
    @Autowired
    SysUserService sysUserService;

//    @PostMapping(value = "upload")
//    public ResultUtil upload(MultipartFile file) {
////        return sysUserService.upload(file);
//
//    }
}

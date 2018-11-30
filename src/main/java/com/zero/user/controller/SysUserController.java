package com.zero.user.controller;

import com.zero.base.controller.BaseController;
import com.zero.user.entity.SysUserEntity;
import com.zero.user.query.SysUserQuery;
import com.zero.user.service.SysUserService;
import com.zero.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "用户")
@RestController
@RequestMapping("/sys/user/")
public class SysUserController extends BaseController<SysUserService, SysUserQuery, SysUserEntity> {
    @Autowired
    SysUserService sysUserService;

    @GetMapping("export")
    @ApiOperation(value = "导出", notes = "导出用户信息")
    public ResultUtil exportExcel(HttpServletResponse response) {
        return sysUserService.exportExcel(response);
    }

    @PostMapping("import")
    public ResultUtil importExcel(@RequestParam MultipartFile file) {
        return sysUserService.importExcel(file);

    }

}

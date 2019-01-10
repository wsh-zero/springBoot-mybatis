package com.wsh.zero.controller;

import com.wsh.util.ResultUtil;
import com.wsh.util.Utils;
import com.wsh.zero.controller.base.BaseController;
import com.wsh.zero.entity.SysUserEntity;
import com.wsh.zero.entity.TestEntity;
import com.wsh.zero.query.SysUserQuery;
import com.wsh.zero.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

    @GetMapping("get/name")
    public ResultUtil getUserNameByUserAmount() {
        return sysUserService.getUserNameByUserAmount();
    }

    @PostMapping("test")
    public ResultUtil test(@Valid @RequestBody TestEntity entity) {
        return ResultUtil.success("成功", Utils.getGson().toJson(entity));
    }

    @PostMapping("eidt")
    public ResultUtil eidt(SysUserEntity entity, MultipartFile file) {
        return sysUserService.eidt(entity, file);
    }

}

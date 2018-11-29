package com.zero.user.controller;

import com.zero.user.entity.SysUserEntity;
import com.zero.user.mapper.SysUserMapper;
import com.zero.user.query.SysUserQuery;
import com.zero.user.service.SysUserService;
import com.zero.user.vo.SysUserVO;
import com.zero.util.ExportExcelUtil;
import com.zero.util.ImportExcelUtil;
import com.zero.util.LayuiTableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户")
@RestController
@RequestMapping("/sys/user/")
public class SysUserController {
    @Autowired
    SysUserService service;
    @Autowired
    SysUserMapper mapper;

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

    @GetMapping("export")
    @ApiOperation(value = "导出", notes = "导出用户信息")
    public void exportExcel(HttpServletResponse response) {
        String[] heart = {"ID", "用户名", "账号", "密码"};
        List<SysUserVO> list = mapper.getList(null, 0, 165534);
        new ExportExcelUtil<>(response, heart, list, "第一次导出");
    }

    @RequestMapping("import")
    public Map<String, Object> importExcel(@RequestParam MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        List<List<String>> lists = ImportExcelUtil.importExcel(file);
        lists.forEach(list -> System.out.println(list.get(0)));
        map.put("success", true);
        map.put("message", "导入成功");
        return map;

    }

}

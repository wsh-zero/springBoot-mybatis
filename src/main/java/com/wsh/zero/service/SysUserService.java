package com.wsh.zero.service;

import com.wsh.util.ExportExcelUtil;
import com.wsh.util.ImportExcelUtil;
import com.wsh.util.ResultUtil;
import com.wsh.zero.entity.SysUserEntity;
import com.wsh.zero.mapper.SysUserMapper;
import com.wsh.zero.query.SysUserQuery;
import com.wsh.zero.service.base.BaseService;
import com.wsh.zero.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Service
public class SysUserService extends BaseService<SysUserMapper, SysUserQuery, SysUserVO, SysUserEntity> {
    @Autowired
    private SysUserMapper sysUserMapper;

    public ResultUtil exportExcel(HttpServletResponse response) {
        String[] heart = {"ID", "用户名", "账号", "密码"};
        List<SysUserVO> list = sysUserMapper.getList(null, 0, 10);
        new ExportExcelUtil<>(response, heart, list, "第一次导出", "wsh");
        return ResultUtil.success("导出成功");
    }

    public ResultUtil importExcel(MultipartFile file) {
        List<List<String>> lists = ImportExcelUtil.importExcel(file);
        for (List<String> list : lists) {
            System.out.println(list.get(0));
        }
        return ResultUtil.success("导入成功");

    }

    public ResultUtil loginDataBaseCheck(String userName, String userPwd) {
        boolean existUserName = sysUserMapper.isExistUserName(userName);
        if (existUserName) {
            boolean existUser = sysUserMapper.isExistUser(userName, userPwd);
            if (existUser) {
                boolean frozen = sysUserMapper.getFrozenValueByUserName(userName);
                if (frozen) {
                    return ResultUtil.failed(1,"账号已经被冻结，请联系管理员!");
                }
                return ResultUtil.success("登录成功!");
            }
            return ResultUtil.failed(1, "用户密码错误!");
        }
        return ResultUtil.failed(1, "用户不存在!");
    }

}

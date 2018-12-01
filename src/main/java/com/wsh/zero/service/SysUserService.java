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
        return new ResultUtil<>("导出成功");
    }

    public ResultUtil importExcel(MultipartFile file) {
        List<List<String>> lists = ImportExcelUtil.importExcel(file);
        for (List<String> list : lists) {
            System.out.println(list.get(0));
        }
        return new ResultUtil<>("导入成功");

    }

    public ResultUtil loginDataBaseCheck(String userName, String userPwd) {
        boolean existUserName = sysUserMapper.isExistUserName(userName);
        if (existUserName) {
            boolean existUser = sysUserMapper.isExistUser(userName, userPwd);
            if (existUser) {
                //登录成功
                return new ResultUtil<>("验证成功!");
            }
            //用户密码错误
            return new ResultUtil<>(1, "用户密码错误!");
        }
        //登录用户不存在
        return new ResultUtil<>(1, "登录用户不存在!");
    }

}

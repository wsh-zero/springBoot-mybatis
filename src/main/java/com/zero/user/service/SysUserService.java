package com.zero.user.service;

import com.zero.user.entity.SysUserEntity;
import com.zero.user.mapper.SysUserMapper;
import com.zero.user.query.SysUserQuery;
import com.zero.user.vo.SysUserVO;
import com.zero.util.ExportExcelUtil;
import com.zero.util.ImportExcelUtil;
import com.zero.util.ResultUtil;
import com.zero.util.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;


@Service
public class SysUserService {
    @Autowired
    private SysUserMapper mapper;

    public TableUtil getSysUserList(SysUserQuery query, int pageNum, int pageSize) {
            TableUtil<SysUserVO> table = new TableUtil<>();
            int count = mapper.getCount(query);
            List<SysUserVO> list = mapper.getList(query, (pageNum - 1) * pageSize, pageSize);
            table.setCount(count);
            table.setData(list);
            return table;
    }

    @Transactional
    public ResultUtil save(SysUserEntity entity) {
        String uuid = UUID.randomUUID().toString();
        entity.setId(uuid);
        mapper.save(entity);
        return new ResultUtil<>("保存成功");
    }

    public ResultUtil exportExcel(HttpServletResponse response) {
        String[] heart = {"ID", "用户名", "账号", "密码"};
        List<SysUserVO> list = mapper.getList(null, 0, 10);
        new ExportExcelUtil<>(response, heart, list, "第一次导出", "zero");
        return new ResultUtil<>(0, "导出成功", null);
    }

    public ResultUtil importExcel(@RequestParam MultipartFile file) {
        List<List<String>> lists = ImportExcelUtil.importExcel(file);
        lists.forEach(list -> System.out.println(list.get(0)));
        return new ResultUtil<>("导入成功");

    }

}

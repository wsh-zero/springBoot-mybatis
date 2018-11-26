package com.zero.user.service;

import com.zero.user.entity.SysUserEntity;
import com.zero.user.mapper.SysUserMapper;
import com.zero.user.query.SysUserQuery;
import com.zero.user.vo.SysUserVO;
import com.zero.util.LayuiTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class SysUserService {
    @Autowired
    private SysUserMapper mapper;

    public LayuiTableUtil getSysUserList(SysUserQuery query, int pageNum, int pageSize) {
        LayuiTableUtil<SysUserVO> layuiTable = LayuiTableUtil.getInstance();
        int count = mapper.getCount(query);
        List<SysUserVO> list = mapper.getList(query, (pageNum - 1) * pageSize, pageSize);
        layuiTable.setCount(count);
        layuiTable.setData(list);
        return layuiTable;
    }

    @Transactional
    public void save(SysUserEntity entity) {
        String uuid = UUID.randomUUID().toString();
        entity.setId(uuid);
        mapper.save(entity);
    }

}

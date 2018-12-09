package com.wsh.zero.service;

import com.wsh.util.ResultUtil;
import com.wsh.zero.entity.SysRoleEntity;
import com.wsh.zero.mapper.SysRoleMapper;
import com.wsh.zero.query.SysRoleQuery;
import com.wsh.zero.service.base.BaseService;
import com.wsh.zero.vo.SysRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysRoleService extends BaseService<SysRoleMapper, SysRoleQuery, SysRoleVO, SysRoleEntity> {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    public ResultUtil getRoleByUserId(String userId) {
        return ResultUtil.success(sysRoleMapper.getRoleByUserId(userId));
    }
}

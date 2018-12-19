package com.wsh.zero.service;

import com.google.common.base.Strings;
import com.wsh.util.ResultUtil;
import com.wsh.util.Utils;
import com.wsh.zero.controller.aop.anno.SysLogTag;
import com.wsh.zero.entity.SysRoleEntity;
import com.wsh.zero.mapper.SysRoleMapper;
import com.wsh.zero.mapper.SysRolePowerMapper;
import com.wsh.zero.query.SysRoleQuery;
import com.wsh.zero.service.base.BaseService;
import com.wsh.zero.vo.SysRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SysRoleService extends BaseService<SysRoleMapper, SysRoleQuery, SysRoleVO, SysRoleEntity> {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRolePowerMapper sysRolePowerMapper;

    public ResultUtil getRoles() {
        return ResultUtil.success(sysRoleMapper.getRoles());
    }

    @SysLogTag(value = "系统角色", operation = "保存角色")
    @Override
    @Transactional
    public ResultUtil save(SysRoleEntity entity) {
        String roleId = Utils.UUID();
        for (String powerId : entity.getPowerIds())
            if (!Strings.isNullOrEmpty(powerId)) {
                sysRolePowerMapper.save(roleId, powerId);
            }
        entity.setId(roleId);
        return super.save(entity);
    }

    @SysLogTag(value = "系统角色", operation = "修改角色")
    @Override
    @Transactional
    public ResultUtil update(SysRoleEntity entity) {
        String roleId = entity.getId();
        sysRolePowerMapper.delByRoleId(roleId);
        for (String powerId : entity.getPowerIds()) {
            if (!Strings.isNullOrEmpty(powerId)) {
                sysRolePowerMapper.save(roleId, powerId);
            }
        }
        return super.update(entity);
    }

    @SysLogTag(value = "系统角色", operation = "删除角色")
    @Override
    @Transactional
    public ResultUtil del(String[] ids) {
        for (String roleId : ids) {
            if (!Strings.isNullOrEmpty(roleId)) {
                sysRolePowerMapper.delByRoleId(roleId);
            }
        }
        return super.del(ids);
    }
}

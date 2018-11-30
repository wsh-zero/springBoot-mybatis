package com.zero.role.service;

import com.zero.base.service.BaseService;
import com.zero.role.entity.SysRoleEntity;
import com.zero.role.mapper.SysRoleMapper;
import com.zero.role.query.SysRoleQuery;
import com.zero.role.vo.SysRoleVO;
import org.springframework.stereotype.Service;


@Service
public class SysRoleService extends BaseService<SysRoleMapper, SysRoleQuery, SysRoleVO, SysRoleEntity> {

}

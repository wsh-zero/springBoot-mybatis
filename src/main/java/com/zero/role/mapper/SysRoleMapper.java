package com.zero.role.mapper;

import com.zero.base.mapper.BaseMapper;
import com.zero.role.query.SysRoleQuery;
import com.zero.role.entity.SysRoleEntity;
import com.zero.role.vo.SysRoleVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity, SysRoleQuery, SysRoleVO, String> {
}
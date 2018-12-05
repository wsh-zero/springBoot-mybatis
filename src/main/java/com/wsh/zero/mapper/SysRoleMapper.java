package com.wsh.zero.mapper;

import com.wsh.zero.mapper.base.BaseMapper;
import com.wsh.zero.query.SysRoleQuery;
import com.wsh.zero.entity.SysRoleEntity;
import com.wsh.zero.vo.SysRoleVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity, SysRoleQuery, SysRoleVO> {
}
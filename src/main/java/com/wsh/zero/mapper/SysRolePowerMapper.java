package com.wsh.zero.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRolePowerMapper {
    int delByRoleId(@Param("roleId") String roleId);

    int save(@Param("roleId") String roleId, @Param("powerId") String powerId);
}
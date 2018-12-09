package com.wsh.zero.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserRoleMapper {
    int delByUserId(@Param("userId") String userId);

    int save(@Param("userId") String userId, @Param("roleId") String roleId);
}
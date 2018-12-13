package com.wsh.zero.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRolePowerMapper {
    int delByRoleId(@Param("roleId") String roleId);

    int save(@Param("roleId") String roleId, @Param("powerId") String powerId);
}
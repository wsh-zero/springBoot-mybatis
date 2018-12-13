package com.wsh.zero.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRoleMapper {
    int delByUserId(@Param("userId") String userId);

    int save(@Param("userId") String userId, @Param("roleId") String roleId);
}
package com.wsh.zero.mapper;

import com.wsh.zero.entity.SysUserEntity;
import com.wsh.zero.mapper.base.BaseMapper;
import com.wsh.zero.query.SysUserQuery;
import com.wsh.zero.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends BaseMapper<SysUserEntity, SysUserQuery, SysUserVO> {
    SysUserEntity getUserInfoByUserAmount(@Param("userAmount") String userAmount);

    boolean isExistUserAmount(@Param("userAmount") String userAmount);

    boolean isExistUser(@Param("userAmount") String userAmount, @Param("userPwd") String userPwd);

    boolean getFrozenValueByUserName(@Param("userName") String userName);

    String getUserNameByUserAmount(@Param("userAmount") Object userAmount);
}
package com.wsh.zero.mapper;

import com.wsh.zero.entity.SysUserEntity;
import com.wsh.zero.mapper.base.BaseMapper;
import com.wsh.zero.query.SysUserQuery;
import com.wsh.zero.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity, SysUserQuery, SysUserVO, String> {
    SysUserEntity getUserInfoByUserName(@Param("userName") String userName);
}
package com.zero.user.mapper;

import com.zero.base.mapper.BaseMapper;
import com.zero.user.entity.SysUserEntity;
import com.zero.user.query.SysUserQuery;
import com.zero.user.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity, SysUserQuery, SysUserVO, String> {
}
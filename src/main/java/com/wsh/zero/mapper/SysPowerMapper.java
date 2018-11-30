package com.wsh.zero.mapper;

import com.wsh.zero.entity.SysPowerEntity;
import com.wsh.zero.mapper.base.BaseMapper;
import com.wsh.zero.query.SysPowerQuery;
import com.wsh.zero.vo.SysPowerVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysPowerMapper extends BaseMapper<SysPowerEntity, SysPowerQuery, SysPowerVO, String> {
}
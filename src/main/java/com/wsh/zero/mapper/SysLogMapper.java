package com.wsh.zero.mapper;

import com.wsh.zero.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogMapper {
    int save(SysLogEntity entity);
}
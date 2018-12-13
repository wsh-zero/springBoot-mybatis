package com.wsh.zero.mapper;

import com.wsh.zero.entity.SysLogEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLogMapper {
    int save(SysLogEntity entity);
}
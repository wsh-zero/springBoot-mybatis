package com.wsh.zero.service;

import com.wsh.util.ResultUtil;
import com.wsh.zero.entity.SysPowerEntity;
import com.wsh.zero.mapper.SysPowerMapper;
import com.wsh.zero.query.SysPowerQuery;
import com.wsh.zero.service.base.BaseService;
import com.wsh.zero.vo.SysPowerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysPowerService extends BaseService<SysPowerMapper, SysPowerQuery, SysPowerVO, SysPowerEntity> {

    @Autowired
    SysPowerMapper sysPowerMapper;

    public ResultUtil getPowerByRoleId(String userId) {
        return ResultUtil.success(sysPowerMapper.getPowerByRoleId(userId));
    }

}

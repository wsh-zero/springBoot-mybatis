package com.wsh.zero.service;

import com.wsh.util.Consot;
import com.wsh.util.ResultUtil;
import com.wsh.zero.controller.aop.anno.SysLogTag;
import com.wsh.zero.entity.SysPowerEntity;
import com.wsh.zero.mapper.SysPowerMapper;
import com.wsh.zero.query.SysPowerQuery;
import com.wsh.zero.service.base.BaseService;
import com.wsh.zero.vo.SysPowerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class SysPowerService extends BaseService<SysPowerMapper, SysPowerQuery, SysPowerVO, SysPowerEntity> {

    @Autowired
    SysPowerMapper sysPowerMapper;

    public ResultUtil getPowers() {
        return ResultUtil.success(sysPowerMapper.getPowers());
    }

    @SysLogTag(value = "权限信息", operation = "修改状态")
    public ResultUtil updatePowerState(Integer powerState, String id) {
        if (Objects.equals(id, Consot.POWER_MIN_ID)) {
            sysPowerMapper.updatePowerState(powerState, null);
        } else {
            sysPowerMapper.updatePowerState(powerState, id);
        }
        return ResultUtil.success("修改成功");
    }

    @SysLogTag(value = "权限信息", operation = "删除权限")
    @Transactional
    @Override
    public ResultUtil del(String[] id) {
        if (Objects.equals(id, Consot.POWER_MIN_ID)) {
            sysPowerMapper.delByNotPrimaryKey(id[0]);
        } else {
            sysPowerMapper.delByPrimaryKey(id[0]);
            sysPowerMapper.delByParent(id[0]);
        }
        return ResultUtil.success("删除成功!");
    }
}

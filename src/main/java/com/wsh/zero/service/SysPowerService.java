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

    public ResultUtil getPowers() {
        return ResultUtil.success(sysPowerMapper.getPowers());
    }

    /**
     * 数据结构：
     * var nodes = [
     * {id:1, pId:0, name: "父节点1"},
     * {id:11, pId:1, name: "子节点1"},
     * {id:12, pId:1, name: "子节点2"}
     * ];
     *
     * @return
     */
    public ResultUtil getPowerTree() {

        return ResultUtil.success("获取成功", sysPowerMapper.getPowers());
    }
}

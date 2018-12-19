package com.wsh.zero.service;

import com.google.common.collect.Lists;
import com.wsh.util.Consot;
import com.wsh.util.ResultUtil;
import com.wsh.zero.entity.SysPowerEntity;
import com.wsh.zero.mapper.SysPowerMapper;
import com.wsh.zero.query.SysPowerQuery;
import com.wsh.zero.service.base.BaseService;
import com.wsh.zero.vo.MenuTreeVO;
import com.wsh.zero.vo.SysPowerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


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
        List<SysPowerVO> queryList = sysPowerMapper.getPowers();
        List<SysPowerVO> parentList = Lists.newLinkedList();
        for (SysPowerVO vo : queryList) {
            if (Objects.equals(Consot.DEFAULT_UUID, vo.getId())) {
                parentList.add(vo);
            }
        }
        List<SysPowerVO> returnList = Lists.newLinkedList();
        for (SysPowerVO parentMap : parentList) {
            returnList.add(parentMap);
            recursionChildren(parentMap, queryList, returnList );
        }
        return ResultUtil.success("获取成功", returnList);
    }

    // 递归获取子节点
    private static void recursionChildren(SysPowerVO parentMap,
                                          List<SysPowerVO> allList, List<SysPowerVO> returnList) {
        for (SysPowerVO vo : allList) {
            if (Objects.equals(vo.getParent(), parentMap.getId())) {
                returnList.add(vo);
                recursionChildren(vo, allList, returnList);
            }
        }
    }
}

package com.wsh.zero.service;

import com.wsh.util.Consot;
import com.wsh.util.ResultUtil;
import com.wsh.zero.entity.SysMenuEntity;
import com.wsh.zero.mapper.SysMenuMapper;
import com.wsh.zero.vo.SysMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


@Service
public class SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    public ResultUtil getMenuList() {
        List<SysMenuVO> menuList = sysMenuMapper.getMenuList(Consot.DEFAULT_UUID);
        handleData(menuList);
        return ResultUtil.success("获取菜单成功", menuList);
    }

    public ResultUtil calculationLevel(String id, Integer direction) {
        boolean limitLevel = sysMenuMapper.isLimitLevel(id, direction);
        if (limitLevel) {
            return ResultUtil.failed(1, "已经处于数据极限位置");
        }
        sysMenuMapper.calculationLevel(id, direction, Consot.LEVEL_VALUE);
        return ResultUtil.success("移动成功");
    }

    private void handleData(List<SysMenuVO> menuList) {
        if (null != menuList && menuList.size() > 0) {
            for (SysMenuVO item : menuList) {
                List<SysMenuVO> childrenMenu = sysMenuMapper.getMenuList(item.getId());
                if (null != childrenMenu && childrenMenu.size() > 0) {
                    item.setList(childrenMenu);
                }
                handleData(childrenMenu);
            }

        }
    }

    @Transactional
    public ResultUtil save(SysMenuEntity entity) {
        //获取
        BigDecimal maxLevelByParnt = sysMenuMapper.getMaxLevelByParnt(entity.getParent());
        entity.setLevel(maxLevelByParnt.add(new BigDecimal(BigInteger.ONE)));
        sysMenuMapper.save(entity);
        return ResultUtil.success("保存成功");
    }
}


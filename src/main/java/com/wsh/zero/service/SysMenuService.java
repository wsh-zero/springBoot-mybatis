package com.wsh.zero.service;

import com.google.common.collect.Lists;
import com.wsh.util.Consot;
import com.wsh.util.ResultUtil;
import com.wsh.util.Utils;
import com.wsh.zero.controller.aop.anno.SysLogTag;
import com.wsh.zero.entity.SysMenuEntity;
import com.wsh.zero.mapper.SysMenuMapper;
import com.wsh.zero.vo.MenuTreeVO;
import com.wsh.zero.vo.SysMenuVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;


@Service
public class SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    public ResultUtil getMenuList() {
        Subject subject = SecurityUtils.getSubject();
        String userAmount = (String) subject.getPrincipal();
        List<SysMenuVO> menuList = sysMenuMapper.getMenuList(Consot.DEFAULT_UUID, userAmount);
        handleData(menuList, userAmount);
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

    public ResultUtil getByPrimaryKey(String id) {
        return ResultUtil.success("获取成功", sysMenuMapper.getByPrimaryKey(id));
    }

    private void handleData(List<SysMenuVO> menuList, String userAmount) {
        if (null != menuList && menuList.size() > 0) {
            for (SysMenuVO item : menuList) {
                List<SysMenuVO> childrenMenu = sysMenuMapper.getMenuList(item.getId(), userAmount);
                if (null != childrenMenu && childrenMenu.size() > 0) {
                    item.setList(childrenMenu);
                }
                handleData(childrenMenu, userAmount);
            }

        }
    }

    @SysLogTag(value = "菜单目录", operation = "保存菜单")
    @Transactional
    public ResultUtil save(SysMenuEntity entity) {
        //获取
        BigDecimal maxLevelByParnt = sysMenuMapper.getMaxLevelByParnt(entity.getParent());
        entity.setLevel(maxLevelByParnt.add(new BigDecimal(BigInteger.ONE)));
        entity.setId(Utils.UUID());
        sysMenuMapper.save(entity);
        return ResultUtil.success("保存成功");
    }

    @SysLogTag(value = "菜单目录", operation = "删除菜单")
    @Transactional
    public ResultUtil del(String id) {
        recursionDel(id);
        return ResultUtil.success("删除成功!");
    }

    // 递归删除
    private void recursionDel(String id) {
        String[] ids = sysMenuMapper.getIdsByParent(id);
        sysMenuMapper.delByParent(id);
        for (String item : ids) {
            recursionDel(item);
        }
        sysMenuMapper.del(id);
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
    public ResultUtil getMenuTree() {
        List<MenuTreeVO> queryList = sysMenuMapper.getAll();
        List<MenuTreeVO> parentList = Lists.newLinkedList();
        for (MenuTreeVO vo : queryList) {
            if (Objects.equals(Consot.DEFAULT_UUID, vo.getId())) {
                parentList.add(vo);
            }
        }
        List<MenuTreeVO> returnList = Lists.newLinkedList();
        for (MenuTreeVO parentMap : parentList) {
            returnList.add(parentMap);
            recursionChildren(parentMap, queryList, returnList);
        }
        return ResultUtil.success("获取成功", returnList);
    }

    // 递归获取子节点
    private static void recursionChildren(MenuTreeVO parentMap,
                                          List<MenuTreeVO> allList, List<MenuTreeVO> returnList) {
        for (MenuTreeVO vo : allList) {
            if (Objects.equals(vo.getParent(), parentMap.getId())) {
                returnList.add(vo);
                recursionChildren(vo, allList, returnList);
            }
        }
    }
}


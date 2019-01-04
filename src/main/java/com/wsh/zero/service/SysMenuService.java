package com.wsh.zero.service;

import com.google.common.base.Strings;
import com.wsh.util.Consot;
import com.wsh.util.ResultUtil;
import com.wsh.util.Utils;
import com.wsh.zero.controller.aop.anno.SysLogTag;
import com.wsh.zero.entity.SysMenuEntity;
import com.wsh.zero.mapper.SysMenuMapper;
import com.wsh.zero.vo.SysMenuVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysPowerService sysPowerService;

    public ResultUtil getMenuList() {
        //获取登录用户
        Subject subject = SecurityUtils.getSubject();
        String userAmount = (String) subject.getPrincipal();
        if (Strings.isNullOrEmpty(userAmount)) {
            return ResultUtil.failed(1001, "您还没有登录！");
        }
        Set<String> loginUserPowerPath = sysPowerService.getPowerPathByUserAmount(userAmount);
        List<SysMenuVO> menuList = sysMenuMapper.getMenuList(Consot.ALL_ZERO_UUID);
        handleData(menuList, loginUserPowerPath);
        return ResultUtil.success("获取菜单成功", menuList);
    }

    private void handleData(List<SysMenuVO> menuList, Set<String> loginUserPowerPath) {

        if (null != menuList && menuList.size() > 0) {
            Iterator<SysMenuVO> it = menuList.iterator();
            while (it.hasNext()) {
                SysMenuVO vo = it.next();
                //按照用户权限过滤菜单
                if (!Objects.equals(vo.getParent(), Consot.ALL_ZERO_UUID)
                        && !Strings.isNullOrEmpty(vo.getJump())
                        && !loginUserPowerPath.contains(vo.getJump())) {
                    it.remove();
                }
                List<SysMenuVO> childrenMenu = sysMenuMapper.getMenuList(vo.getId());
                if (null != childrenMenu && childrenMenu.size() > 0) {
//                    if (Strings.isNullOrEmpty(vo.getJump())) {
//                        /**
//                         * 判断用户拥有权限,在子列表中是否存在
//                         */
//                        Set<String> jumps = childrenMenu.stream().map(SysMenuVO::getJump).collect(Collectors.toSet());
//                        int size = jumps.size();
//                        jumps.removeAll(loginUserPowerPath);
//                        if (size == jumps.size()) {
//                            it.remove();
//                        }
//                    }
                    vo.setList(childrenMenu);
                }
                handleData(childrenMenu, loginUserPowerPath);
            }

        }
    }

    @SysLogTag(value = "菜单目录", operation = "移动菜单")
    @Transactional
    public ResultUtil calculationLevel(String id, Integer direction) {
        /**
         * 向下移动查出当前的level值,查出比当前大的level值,进行交换
         */
        Integer level = sysMenuMapper.getLevelById(id);
        Map<String, String> beforeOrAfterLevel = sysMenuMapper.getBeforeOrAfterLevel(id, level, direction);
        if (null == beforeOrAfterLevel || beforeOrAfterLevel.size() == 0) {
            return ResultUtil.success("已经处于数据极限位置");
        }
        sysMenuMapper.updateLevelById(id, Integer.valueOf(String.valueOf(beforeOrAfterLevel.get("level"))));
        sysMenuMapper.updateLevelById(beforeOrAfterLevel.get("id"), level);
        return ResultUtil.success("移动成功");
    }

    public ResultUtil getByPrimaryKey(String id) {
        return ResultUtil.success("获取成功", sysMenuMapper.getByPrimaryKey(id));
    }


    @SysLogTag(value = "菜单目录", operation = "保存菜单")
    @Transactional
    public ResultUtil save(SysMenuEntity entity) {
        if (null != entity) {
            if (Strings.isNullOrEmpty(entity.getId())) {
                if (Strings.isNullOrEmpty(entity.getParent())) {
                    return ResultUtil.failed(1, "获取父级编号失败");
                }
                Integer maxLevel = sysMenuMapper.getMaxLevelByParent(entity.getParent());
                entity.setLevel(maxLevel == null ? 1 : maxLevel + 1);
                String uuid = Utils.UUID();
                entity.setId(uuid);
                sysMenuMapper.save(entity);
                return ResultUtil.success("保存成功", uuid);
            } else {
                sysMenuMapper.update(entity);
                return ResultUtil.success("保存成功");
            }
        }
        return ResultUtil.failed(1, "获取菜单数据失败");
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
        return ResultUtil.success("获取成功", sysMenuMapper.getMenuTree());
    }
}


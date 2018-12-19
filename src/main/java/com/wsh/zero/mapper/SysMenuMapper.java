package com.wsh.zero.mapper;

import com.wsh.zero.entity.SysMenuEntity;
import com.wsh.zero.vo.MenuTreeVO;
import com.wsh.zero.vo.SysMenuVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysMenuMapper {
    List<SysMenuVO> getMenuList(@Param("parent") String parent, @Param("userAmount") String userAmount);

    int save(SysMenuEntity entity);

    int update(SysMenuEntity entity);

    int del(@Param("id") String id);

    int delByParent(@Param("parent") String parent);

    String[] getIdsByParent(@Param("parent") String parent);

    List<MenuTreeVO> getMenuTree();

    Integer getMaxLevelByParent(@Param("parent") String parent);

    SysMenuVO getByPrimaryKey(@Param("id") String id);

    Integer getLevelById(@Param("id") String id);

    /**
     * 获取数据之前或之后的level值
     *
     * @param id        移动编号(用来定位到同级)
     * @param level     参考level值
     * @param direction 方向（1 下  其他值 上）
     * @return
     */
    Map<String, String> getBeforeOrAfterLevel(@Param("id") String id, @Param("level") Integer level, @Param("direction") Integer direction);

    int updateLevelById(@Param("id") String id, @Param("level") Integer level);
}
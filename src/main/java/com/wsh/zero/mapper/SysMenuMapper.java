package com.wsh.zero.mapper;

import com.wsh.zero.entity.SysMenuEntity;
import com.wsh.zero.vo.SysMenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenuVO> getMenuList(@Param("parent") String parent, @Param("userName") String userName);

    int save(SysMenuEntity entity);

    BigDecimal getMaxLevelByParnt(@Param("parent") String parent);

    /**
     * 实现数据上下移动
     *
     * @param id         移动编号
     * @param direction  方向（1 下  其他值 上）
     * @param levelValue 变化常量
     * @return
     */
    int calculationLevel(@Param("id") String id, @Param("direction") Integer direction, @Param("levelValue") BigDecimal levelValue);

    /**
     * 移动时判断是否是极限位置（最上  最下），处于极限位置不能在移动
     *
     * @param id        移动编号
     * @param direction 方向（1 下  其他值 上）
     * @return
     */
    boolean isLimitLevel(@Param("id") String id, @Param("direction") Integer direction);
}
package com.wsh.zero.mapper.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @param <E> 实体类
 * @param <Q> 查询条件类
 * @param <V> 返回页面类
 */
public interface BaseMapper<E, Q, V> {
    /**
     * @param q        通过条件获取count数量
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<V> getList(@Param("query") Q q,
                    @Param("pageNum") int pageNum,
                    @Param("pageSize") int pageSize);

    /**
     * @param q 通过条件获取count数量
     * @return
     */
    int getCount(@Param("query") Q q);

    /**
     * @param id 通过主键获取一条数据
     * @return
     */
    E getByPrimarykey(String id);

    /**
     * @param e 保存数据
     * @return
     */
    int save(E e);

    /**
     * @param id 通过主键删除一条数据
     * @return
     */
    int delByPrimarykey(String id);

    /**
     * @param e 修改数据
     * @return
     */
    int update(E e);
}
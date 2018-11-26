package com.zero.base.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @param <E>
 * @param <Q>
 * @param <V>
 */
public interface BaseMapper<E, Q, V, ID> {
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
    E getByPrimarykey(ID id);

    /**
     * @param e 保存数据
     * @return
     */
    int save(E e);

    /**
     * @param id 通过主键删除一条数据
     * @return
     */
    int delByPrimarykey(ID id);

    /**
     * @param e 修改数据
     * @return
     */
    int update(E e);
}
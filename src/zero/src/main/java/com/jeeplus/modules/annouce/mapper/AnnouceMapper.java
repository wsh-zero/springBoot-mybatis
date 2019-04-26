/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.annouce.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.annouce.entity.Annouce;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 发布公告管理MAPPER接口
 * @author xy
 * @version 2019-02-28
 */
@MyBatisMapper
public interface AnnouceMapper extends BaseMapper<Annouce> {

    List findByUserId(@Param("userid") String userid);

    /**
     * 查询当天记录数
     */
    @Select("execute count(*) from annouce where timediff(publishtime, #{date}) = 0")
    Integer selectDateCount(Date date);

    List<Annouce> selectRecvList(Annouce annouce, String curUser, Integer pageStart, Integer pageSize);

    Integer selectRecvListCount(Annouce annouce, String curUser);
}
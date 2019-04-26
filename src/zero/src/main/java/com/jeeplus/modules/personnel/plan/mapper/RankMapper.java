/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.kotlin.dto.MapDto;
import com.jeeplus.modules.personnel.plan.entity.Rank;

import java.util.List;

/**
 * 职级管理MAPPER接口
 *
 * @author 王伟
 * @version 2019-02-14
 */
@MyBatisMapper
public interface RankMapper extends BaseMapper<Rank> {

    Rank find(Rank rank);

    List<MapDto> getIdAndName();

    int count(Rank rank);

}
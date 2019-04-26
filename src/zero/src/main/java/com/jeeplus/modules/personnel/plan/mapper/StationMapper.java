/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.plan.entity.Station;

/**
 * 岗位管理MAPPER接口
 * @author 王伟
 * @version 2019-02-15
 */
@MyBatisMapper
public interface StationMapper extends BaseMapper<Station> {

    Station find(Station station);
    int count(Station station);

    Integer getMaxNumber();

}
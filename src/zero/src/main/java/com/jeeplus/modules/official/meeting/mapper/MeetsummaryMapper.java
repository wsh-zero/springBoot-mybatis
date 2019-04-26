/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.meeting.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.official.meeting.entity.Meetsummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会议纪要管理MAPPER接口
 * @author xy
 * @version 2019-03-04
 */
@MyBatisMapper
public interface MeetsummaryMapper extends BaseMapper<Meetsummary> {

    List findByUserId(@Param("userid") String userid);
	
}
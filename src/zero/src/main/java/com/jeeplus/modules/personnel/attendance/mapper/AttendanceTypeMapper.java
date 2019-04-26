/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.attendance.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.attendance.entity.AttendanceType;

/**
 * 考勤类型MAPPER接口
 * @author 王伟
 * @version 2019-02-19
 */
@MyBatisMapper
public interface AttendanceTypeMapper extends BaseMapper<AttendanceType> {

    AttendanceType find(AttendanceType attendanceType);
	
}
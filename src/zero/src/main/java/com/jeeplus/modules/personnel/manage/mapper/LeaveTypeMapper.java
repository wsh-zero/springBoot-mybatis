/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.manage.entity.LeaveType;

/**
 * 离职类型MAPPER接口
 * @author 王伟
 * @version 2019-02-14
 */
@MyBatisMapper
public interface LeaveTypeMapper extends BaseMapper<LeaveType> {
    LeaveType find(LeaveType leaveType);

    int count(LeaveType leaveType);
	
}
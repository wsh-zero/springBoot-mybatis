/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.manage.entity.StaffStatus;

/**
 * 员工状态MAPPER接口
 * @author 王伟
 * @version 2019-02-14
 */
@MyBatisMapper
public interface StaffStatusMapper extends BaseMapper<StaffStatus> {
    StaffStatus find(StaffStatus staffStatus);

    int count(StaffStatus staffStatus);
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.salary.entity.SallaryManager;

/**
 * 员工薪酬管理MAPPER接口
 * @author 王伟
 * @version 2019-03-18
 */
@MyBatisMapper
public interface SallaryManagerMapper extends BaseMapper<SallaryManager> {

    SallaryManager find(SallaryManager sallaryManager);

    int count(SallaryManager sallaryManager);
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.plan.entity.Education;

/**
 * 学历管理MAPPER接口
 * @author 王伟
 * @version 2019-01-11
 */
@MyBatisMapper
public interface EducationMapper extends BaseMapper<Education> {

    Education find(Education education);

    int count(Education education);

}
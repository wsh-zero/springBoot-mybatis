/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.plan.entity.JobCategory;

/**
 * 岗位类别MAPPER接口
 * @author 王伟
 * @version 2019-02-14
 */
@MyBatisMapper
public interface JobCategoryMapper extends BaseMapper<JobCategory> {
    JobCategory find(JobCategory jobCategory);

    int count(JobCategory jobCategory);
	
}
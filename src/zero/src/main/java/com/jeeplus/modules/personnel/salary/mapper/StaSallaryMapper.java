/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.salary.entity.StaSallary;

/**
 * 员工薪资配置MAPPER接口
 * @author 王伟
 * @version 2019-03-15
 */
@MyBatisMapper
public interface StaSallaryMapper extends BaseMapper<StaSallary> {

    StaSallary find(StaSallary staSallary);

    int count(StaSallary staSallary);

}
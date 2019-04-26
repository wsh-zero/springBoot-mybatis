/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.plan.entity.GradeBonus;

import java.util.List;

/**
 * 等级MAPPER接口
 * @author 王伟
 * @version 2019-03-14
 */
@MyBatisMapper
public interface GradeBonusMapper extends BaseMapper<GradeBonus> {

    GradeBonus find(GradeBonus gradeBonus);

    int count(GradeBonus gradeBonus);

    List<GradeBonus> getGrade();
	
}
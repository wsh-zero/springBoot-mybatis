/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.plan.entity.TitleBonus;

import java.util.List;

/**
 * 职称MAPPER接口
 * @author 王伟
 * @version 2019-03-14
 */
@MyBatisMapper
public interface TitleBonusMapper extends BaseMapper<TitleBonus> {

    TitleBonus find(TitleBonus titleBonus);

    int count(TitleBonus titleBonus);

    List<TitleBonus> getTitle();
	
}
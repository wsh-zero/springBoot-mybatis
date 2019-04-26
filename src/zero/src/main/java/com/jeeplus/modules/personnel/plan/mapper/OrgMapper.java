/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.mapper;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.plan.entity.Org;

/**
 * 组织MAPPER接口
 * @author 王伟
 * @version 2019-02-14
 */
@MyBatisMapper
public interface OrgMapper extends TreeMapper<Org> {

    Org find(Org org);

    int count(Org org);
	
}
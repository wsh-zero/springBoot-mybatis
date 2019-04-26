/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.achieve.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.achieve.entity.ObjConf;

/**
 * 对象配置MAPPER接口
 * @author ww
 * @version 2019-04-08
 */
@MyBatisMapper
public interface ObjConfMapper extends BaseMapper<ObjConf> {

    ObjConf find(ObjConf objConf);

    int count(ObjConf objConf);
	
}
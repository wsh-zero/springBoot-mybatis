/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.manager.entity.Contact;

/**
 * 员工通讯录MAPPER接口
 * @author 王伟
 * @version 2019-01-30
 */
@MyBatisMapper
public interface ContactMapper extends BaseMapper<Contact> {

    Contact find(Contact contact);

    Contact getName(String id);

    int count(Contact contact);
	
}
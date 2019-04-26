/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.manager.entity.Contact;
import com.jeeplus.modules.personnel.manager.mapper.ContactMapper;

/**
 * 员工通讯录Service
 * @author 王伟
 * @version 2019-01-30
 */
@Service
@Transactional(readOnly = true)
public class ContactService extends CrudService<ContactMapper, Contact> {
	@Autowired
	private ContactMapper contactMapper;

	public Contact get(String id) {
		return super.get(id);
	}
	
	public List<Contact> findList(Contact contact) {
		return super.findList(contact);
	}
	
	public Page<Contact> findPage(Page<Contact> page, Contact contact) {
		return super.findPage(page, contact);
	}
	
	@Transactional(readOnly = false)
	public void save(Contact contact) {
		super.save(contact);
	}
	
	@Transactional(readOnly = false)
	public void delete(Contact contact) {
		super.deleteByLogic(contact);
	}

	/**
	 *条件查询
	 * @param contact
	 * @return
     */
	public Contact find (Contact contact){
		return  contactMapper.find(contact);
	}

	public Contact getName(String id) {

	return contactMapper.getName(id);


	}
	public int count(Contact contact){
		return contactMapper.count(contact);
	}
}
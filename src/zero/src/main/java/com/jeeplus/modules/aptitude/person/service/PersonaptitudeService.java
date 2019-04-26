/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.aptitude.person.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.aptitude.person.entity.Personaptitude;
import com.jeeplus.modules.aptitude.person.mapper.PersonaptitudeMapper;

/**
 * 个人资质管理Service
 * @author xy
 * @version 2019-02-22
 */
@Service
@Transactional(readOnly = true)
public class PersonaptitudeService extends CrudService<PersonaptitudeMapper, Personaptitude> {

	public Personaptitude get(String id) {
		return super.get(id);
	}
	
	public List<Personaptitude> findList(Personaptitude personaptitude) {
		return super.findList(personaptitude);
	}
	
	public Page<Personaptitude> findPage(Page<Personaptitude> page, Personaptitude personaptitude) {
		return super.findPage(page, personaptitude);
	}
	
	@Transactional(readOnly = false)
	public void save(Personaptitude personaptitude) {
		super.save(personaptitude);
	}
	
	@Transactional(readOnly = false)
	public void delete(Personaptitude personaptitude) {
		super.delete(personaptitude);
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oatest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oatest.entity.Oatest;
import com.jeeplus.modules.oatest.mapper.OatestMapper;

/**
 * 随意测试Service
 * @author xy
 * @version 2019-01-25
 */
@Service
@Transactional(readOnly = true)
public class OatestService extends CrudService<OatestMapper, Oatest> {

	public Oatest get(String id) {
		return super.get(id);
	}
	
	public List<Oatest> findList(Oatest oatest) {
		return super.findList(oatest);
	}
	
	public Page<Oatest> findPage(Page<Oatest> page, Oatest oatest) {
		return super.findPage(page, oatest);
	}
	
	@Transactional(readOnly = false)
	public void save(Oatest oatest) {
		super.save(oatest);
	}
	
	@Transactional(readOnly = false)
	public void delete(Oatest oatest) {
		super.delete(oatest);
	}
	
}
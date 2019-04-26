/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.aptitude.cooper.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.aptitude.cooper.entity.Cooperaptitude;
import com.jeeplus.modules.aptitude.cooper.mapper.CooperaptitudeMapper;

/**
 * 合作个人资质管理Service
 * @author xy
 * @version 2019-02-22
 */
@Service
@Transactional(readOnly = true)
public class CooperaptitudeService extends CrudService<CooperaptitudeMapper, Cooperaptitude> {

	public Cooperaptitude get(String id) {
		return super.get(id);
	}
	
	public List<Cooperaptitude> findList(Cooperaptitude cooperaptitude) {
		return super.findList(cooperaptitude);
	}
	
	public Page<Cooperaptitude> findPage(Page<Cooperaptitude> page, Cooperaptitude cooperaptitude) {
		return super.findPage(page, cooperaptitude);
	}
	
	@Transactional(readOnly = false)
	public void save(Cooperaptitude cooperaptitude) {
		super.save(cooperaptitude);
	}
	
	@Transactional(readOnly = false)
	public void delete(Cooperaptitude cooperaptitude) {
		super.delete(cooperaptitude);
	}
	
}
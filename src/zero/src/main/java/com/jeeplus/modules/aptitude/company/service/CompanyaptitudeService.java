/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.aptitude.company.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.aptitude.company.entity.Companyaptitude;
import com.jeeplus.modules.aptitude.company.mapper.CompanyaptitudeMapper;

/**
 * 公司资质管理Service
 * @author xy
 * @version 2019-03-04
 */
@Service
@Transactional(readOnly = true)
public class CompanyaptitudeService extends CrudService<CompanyaptitudeMapper, Companyaptitude> {

	public Companyaptitude get(String id) {
		return super.get(id);
	}
	
	public List<Companyaptitude> findList(Companyaptitude companyaptitude) {
		return super.findList(companyaptitude);
	}
	
	public Page<Companyaptitude> findPage(Page<Companyaptitude> page, Companyaptitude companyaptitude) {
		return super.findPage(page, companyaptitude);
	}
	
	@Transactional(readOnly = false)
	public void save(Companyaptitude companyaptitude) {
		super.save(companyaptitude);
	}
	
	@Transactional(readOnly = false)
	public void delete(Companyaptitude companyaptitude) {
		super.delete(companyaptitude);
	}
	
}
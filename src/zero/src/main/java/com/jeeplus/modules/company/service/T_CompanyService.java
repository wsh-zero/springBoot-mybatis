/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.company.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.company.entity.T_Company;
import com.jeeplus.modules.company.mapper.T_CompanyMapper;

/**
 * 公司Service
 * @author xy
 * @version 2019-01-25
 */
@Service
@Transactional(readOnly = true)
public class T_CompanyService extends CrudService<T_CompanyMapper, T_Company> {

	public T_Company get(String id) {
		return super.get(id);
	}
	
	public List<T_Company> findList(T_Company t_Company) {
		return super.findList(t_Company);
	}
	
	public Page<T_Company> findPage(Page<T_Company> page, T_Company t_Company) {
		return super.findPage(page, t_Company);
	}
	
	@Transactional(readOnly = false)
	public void save(T_Company t_Company) {
		super.save(t_Company);
	}
	
	@Transactional(readOnly = false)
	public void delete(T_Company t_Company) {
		super.delete(t_Company);
	}
	
}
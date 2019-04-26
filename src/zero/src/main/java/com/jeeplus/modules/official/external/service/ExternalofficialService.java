/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.external.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.official.external.entity.Externalofficial;
import com.jeeplus.modules.official.external.mapper.ExternalofficialMapper;

/**
 * 公司对外公文管理Service
 * @author xy
 * @version 2019-02-25
 */
@Service
@Transactional(readOnly = true)
public class ExternalofficialService extends CrudService<ExternalofficialMapper, Externalofficial> {

	public Externalofficial get(String id) {
		return super.get(id);
	}
	
	public List<Externalofficial> findList(Externalofficial externalofficial) {
		return super.findList(externalofficial);
	}
	
	public Page<Externalofficial> findPage(Page<Externalofficial> page, Externalofficial externalofficial) {
		return super.findPage(page, externalofficial);
	}
	
	@Transactional(readOnly = false)
	public void save(Externalofficial externalofficial) {
		super.save(externalofficial);
	}
	
	@Transactional(readOnly = false)
	public void delete(Externalofficial externalofficial) {
		super.delete(externalofficial);
	}
	
}
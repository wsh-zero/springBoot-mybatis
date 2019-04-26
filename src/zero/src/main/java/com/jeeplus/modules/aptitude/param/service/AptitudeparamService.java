/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.aptitude.param.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.aptitude.param.entity.Aptitudeparam;
import com.jeeplus.modules.aptitude.param.mapper.AptitudeparamMapper;

/**
 * 资质参数配置Service
 * @author xy
 * @version 2019-02-22
 */
@Service
@Transactional(readOnly = true)
public class AptitudeparamService extends CrudService<AptitudeparamMapper, Aptitudeparam> {

	public Aptitudeparam get(String id) {
		return super.get(id);
	}
	
	public List<Aptitudeparam> findList(Aptitudeparam aptitudeparam) {
		return super.findList(aptitudeparam);
	}
	
	public Page<Aptitudeparam> findPage(Page<Aptitudeparam> page, Aptitudeparam aptitudeparam) {
		return super.findPage(page, aptitudeparam);
	}
	
	@Transactional(readOnly = false)
	public void save(Aptitudeparam aptitudeparam) {
		super.save(aptitudeparam);
	}
	
	@Transactional(readOnly = false)
	public void delete(Aptitudeparam aptitudeparam) {
		super.delete(aptitudeparam);
	}
	
}
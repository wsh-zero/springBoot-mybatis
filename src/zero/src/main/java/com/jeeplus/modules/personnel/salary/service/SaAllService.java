/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.salary.entity.SaAll;
import com.jeeplus.modules.personnel.salary.mapper.SaAllMapper;

/**
 * 薪资账套设置Service
 * @author 王伟
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class SaAllService extends CrudService<SaAllMapper, SaAll> {

	public SaAll get(String id) {
		return super.get(id);
	}
	
	public List<SaAll> findList(SaAll saAll) {
		return super.findList(saAll);
	}
	
	public Page<SaAll> findPage(Page<SaAll> page, SaAll saAll) {
		return super.findPage(page, saAll);
	}
	
	@Transactional(readOnly = false)
	public void save(SaAll saAll) {
		super.save(saAll);
	}
	
	@Transactional(readOnly = false)
	public void delete(SaAll saAll) {
		super.delete(saAll);
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.achieve.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.achieve.entity.PerStandard;
import com.jeeplus.modules.personnel.achieve.mapper.PerStandardMapper;

/**
 * 评定标准Service
 * @author ww
 * @version 2019-04-08
 */
@Service
@Transactional(readOnly = true)
public class PerStandardService extends CrudService<PerStandardMapper, PerStandard> {
	@Autowired
	private PerStandardMapper perStandardMapper;

	public PerStandard get(String id) {
		return super.get(id);
	}
	
	public List<PerStandard> findList(PerStandard perStandard) {
		return super.findList(perStandard);
	}
	
	public Page<PerStandard> findPage(Page<PerStandard> page, PerStandard perStandard) {
		return super.findPage(page, perStandard);
	}
	
	@Transactional(readOnly = false)
	public void save(PerStandard perStandard) {
		super.save(perStandard);
	}
	
	@Transactional(readOnly = false)
	public void delete(PerStandard perStandard) {
		super.delete(perStandard);
	}

	public PerStandard find(PerStandard perStandard){
		return perStandardMapper.find(perStandard);
	}

	public int count(PerStandard perStandard){
		return perStandardMapper.count(perStandard);
	}
	
}
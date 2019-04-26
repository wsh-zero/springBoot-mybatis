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
import com.jeeplus.modules.personnel.achieve.entity.PerConf;
import com.jeeplus.modules.personnel.achieve.mapper.PerConfMapper;

/**
 * 绩效配置Service
 * @author ww
 * @version 2019-04-08
 */
@Service
@Transactional(readOnly = true)
public class PerConfService extends CrudService<PerConfMapper, PerConf> {
	@Autowired
	private PerConfMapper perConfMapper;

	public PerConf get(String id) {
		return super.get(id);
	}
	
	public List<PerConf> findList(PerConf perConf) {
		return super.findList(perConf);
	}
	
	public Page<PerConf> findPage(Page<PerConf> page, PerConf perConf) {
		return super.findPage(page, perConf);
	}
	
	@Transactional(readOnly = false)
	public void save(PerConf perConf) {
		super.save(perConf);
	}
	
	@Transactional(readOnly = false)
	public void delete(PerConf perConf) {
		super.delete(perConf);
	}

	public PerConf find(String name){
		return perConfMapper.find(name);
	}
	
}
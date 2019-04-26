/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.seal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.seal.entity.Seal;
import com.jeeplus.modules.seal.mapper.SealMapper;

/**
 * 使用印章管理Service
 * @author xy
 * @version 2019-02-22
 */
@Service
@Transactional(readOnly = true)
public class SealService extends CrudService<SealMapper, Seal> {

	public Seal get(String id) {
		return super.get(id);
	}
	
	public List<Seal> findList(Seal seal) {
		return super.findList(seal);
	}
	
	public Page<Seal> findPage(Page<Seal> page, Seal seal) {
		return super.findPage(page, seal);
	}
	
	@Transactional(readOnly = false)
	public void save(Seal seal) {
		super.save(seal);
	}
	
	@Transactional(readOnly = false)
	public void delete(Seal seal) {
		super.delete(seal);
	}
	
}
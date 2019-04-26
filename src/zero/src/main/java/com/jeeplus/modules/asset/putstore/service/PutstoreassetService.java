/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.asset.putstore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.asset.putstore.entity.Putstoreasset;
import com.jeeplus.modules.asset.putstore.mapper.PutstoreassetMapper;

/**
 * 固定资产入库管理Service
 * @author xy
 * @version 2019-02-22
 */
@Service
@Transactional(readOnly = true)
public class PutstoreassetService extends CrudService<PutstoreassetMapper, Putstoreasset> {

	public Putstoreasset get(String id) {
		return super.get(id);
	}
	
	public List<Putstoreasset> findList(Putstoreasset putstoreasset) {
		return super.findList(putstoreasset);
	}
	
	public Page<Putstoreasset> findPage(Page<Putstoreasset> page, Putstoreasset putstoreasset) {
		return super.findPage(page, putstoreasset);
	}
	
	@Transactional(readOnly = false)
	public void save(Putstoreasset putstoreasset) {
		super.save(putstoreasset);
	}
	
	@Transactional(readOnly = false)
	public void delete(Putstoreasset putstoreasset) {
		super.delete(putstoreasset);
	}
	
}
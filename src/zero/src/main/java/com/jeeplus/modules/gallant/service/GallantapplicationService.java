/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.gallant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.gallant.entity.Gallantapplication;
import com.jeeplus.modules.gallant.mapper.GallantapplicationMapper;

/**
 * 招骋需求Service
 * @author xy
 * @version 2019-01-30
 */
@Service
@Transactional(readOnly = true)
public class GallantapplicationService extends CrudService<GallantapplicationMapper, Gallantapplication> {

	public Gallantapplication get(String id) {
		return super.get(id);
	}
	
	public List<Gallantapplication> findList(Gallantapplication gallantapplication) {
		return super.findList(gallantapplication);
	}
	
	public Page<Gallantapplication> findPage(Page<Gallantapplication> page, Gallantapplication gallantapplication) {
		return super.findPage(page, gallantapplication);
	}
	
	@Transactional(readOnly = false)
	public void save(Gallantapplication gallantapplication) {
		super.save(gallantapplication);
	}
	
	@Transactional(readOnly = false)
	public void delete(Gallantapplication gallantapplication) {
		super.delete(gallantapplication);
	}
	
}
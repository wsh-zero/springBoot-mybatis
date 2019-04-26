/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resume.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.resume.entity.Resumemanager;
import com.jeeplus.modules.resume.mapper.ResumemanagerMapper;

/**
 * 简历管理Service
 * @author xy
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class ResumemanagerService extends CrudService<ResumemanagerMapper, Resumemanager> {

	public Resumemanager get(String id) {
		return super.get(id);
	}
	
	public List<Resumemanager> findList(Resumemanager resumemanager) {
		return super.findList(resumemanager);
	}
	
	public Page<Resumemanager> findPage(Page<Resumemanager> page, Resumemanager resumemanager) {
		return super.findPage(page, resumemanager);
	}
	
	@Transactional(readOnly = false)
	public void save(Resumemanager resumemanager) {
		super.save(resumemanager);
	}
	
	@Transactional(readOnly = false)
	public void delete(Resumemanager resumemanager) {
		super.delete(resumemanager);
	}
	
}
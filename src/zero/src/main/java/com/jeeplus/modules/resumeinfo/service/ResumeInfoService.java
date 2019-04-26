/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resumeinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.resumeinfo.entity.ResumeInfo;
import com.jeeplus.modules.resumeinfo.mapper.ResumeInfoMapper;

/**
 * 简历管理Service
 * @author chentao
 * @version 2019-04-09
 */
@Service
@Transactional(readOnly = true)
public class ResumeInfoService extends CrudService<ResumeInfoMapper, ResumeInfo> {

	public ResumeInfo get(String id) {
		ResumeInfo info = super.get(id);
		info.setJobIntension(mapper.getJobIntension(id));
		info.setWorkExps(mapper.getWorpExp(id));
		info.setProjectExps(mapper.getProjectExp(id));
		info.setEduBackgrounds(mapper.getEduBack(id));
		return info;
	}
	
	public List<ResumeInfo> findList(ResumeInfo resumeInfo) {
		return super.findList(resumeInfo);
	}
	
	public Page<ResumeInfo> findPage(Page<ResumeInfo> page, ResumeInfo resumeInfo) {
		return super.findPage(page, resumeInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ResumeInfo resumeInfo) {
		super.save(resumeInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResumeInfo resumeInfo) {
		super.delete(resumeInfo);
	}

	@Transactional(readOnly = false)
	public void insert(ResumeInfo resumeInfo) {
		mapper.insert(resumeInfo);
		mapper.insertWorkExp(resumeInfo.getWorkExps(), resumeInfo.getId());
		mapper.insertProjectExp(resumeInfo.getProjectExps(), resumeInfo.getId());
		mapper.insertEduBackground(resumeInfo.getEduBackgrounds(), resumeInfo.getId());
		mapper.insertJobIntension(resumeInfo.getJobIntension(), resumeInfo.getId());
	}
}
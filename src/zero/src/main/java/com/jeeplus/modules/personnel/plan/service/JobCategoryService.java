/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.service;

import java.util.List;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import org.activiti.engine.runtime.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.plan.entity.JobCategory;
import com.jeeplus.modules.personnel.plan.mapper.JobCategoryMapper;

/**
 * 岗位类别Service
 * @author 王伟
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class JobCategoryService extends CrudService<JobCategoryMapper, JobCategory> {
	@Autowired
	private JobCategoryMapper jobCategoryMapper;

	public JobCategory get(String id) {
		return super.get(id);
	}
	
	public List<JobCategory> findList(JobCategory jobCategory) {
		return super.findList(jobCategory);
	}
	
	public Page<JobCategory> findPage(Page<JobCategory> page, JobCategory jobCategory) {
		return super.findPage(page, jobCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(JobCategory jobCategory) {
		super.save(jobCategory);
	}

	@Transactional(readOnly = false)
	public AjaxJson preserve(JobCategory jobCategory) {
		AjaxJson j = new AjaxJson();
		try{
			if (jobCategory.getIsNewRecord()) {
				if (StringUtils.isNotBlank(jobCategory.getJobType())) {
					JobCategory category = new JobCategory();
					category.setJobType(jobCategory.getJobType());
					int count = jobCategoryMapper.count(category);
					if (count > 0) {
						j.setSuccess(false);
						j.setMsg("岗位类型重复");
						return j;
					}
				}
			}else {
				if (StringUtils.isNotBlank(jobCategory.getJobType())) {
					JobCategory category = new JobCategory();
					category.setJobType(jobCategory.getJobType());
					JobCategory job = jobCategoryMapper.find(category);
					if (!job.getId().equals(jobCategory.getId())&&job!=null) {
						j.setSuccess(false);
						j.setMsg("岗位类型重复");
						return j;
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		super.save(jobCategory);
		j.setSuccess(true);
		j.setMsg("保存岗位类型成功");
		return j;
	}
	
	@Transactional(readOnly = false)
	public void delete(JobCategory jobCategory) {
		super.deleteByLogic(jobCategory);
	}
	
}
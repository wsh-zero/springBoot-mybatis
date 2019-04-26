/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.service;

import java.util.List;


import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.plan.entity.Education;
import com.jeeplus.modules.personnel.plan.mapper.EducationMapper;

/**
 * 学历管理Service
 * @author 王伟
 * @version 2019-01-11
 */
@Service
@Transactional(readOnly = true)
public class EducationService extends CrudService<EducationMapper, Education> {

	@Autowired
	private EducationMapper educationMapper;

	public Education get(String id) {
		return super.get(id);
	}

	public List<Education> findList(Education education) {
		return super.findList(education);
	}

	public Page<Education> findPage(Page<Education> page, Education education) {
		return super.findPage(page, education);
	}

	@Transactional(readOnly = false)
	public void save(Education education) {
		super.save(education);
	}

	@Transactional(readOnly = false)
	public AjaxJson preserve(Education education) {
		AjaxJson j =new AjaxJson();
		if (StringUtils.isNotBlank(education.getEducate())){
			Education e = new Education();
			e.setEducate(education.getEducate());
			int count = educationMapper.count(e);
			if (count > 0){
				j.setSuccess(false);
				j.setMsg("学历名称重复");
				return j;
			}
		}
		super.save(education);
		j.setSuccess(true);
		j.setMsg("保存学历成功");
		return j;
	}

	@Transactional(readOnly = false)
	public void delete(Education education) {
		super.deleteByLogic(education);
	}

}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.service;

import java.util.List;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.personnel.plan.entity.JobCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.plan.entity.GradeBonus;
import com.jeeplus.modules.personnel.plan.mapper.GradeBonusMapper;

/**
 * 等级Service
 * @author 王伟
 * @version 2019-03-14
 */
@Service
@Transactional(readOnly = true)
public class GradeBonusService extends CrudService<GradeBonusMapper, GradeBonus> {
	@Autowired
	private GradeBonusMapper gradeBonusMapper;

	public GradeBonus get(String id) {
		return super.get(id);
	}
	
	public List<GradeBonus> findList(GradeBonus gradeBonus) {
		return super.findList(gradeBonus);
	}
	
	public Page<GradeBonus> findPage(Page<GradeBonus> page, GradeBonus gradeBonus) {
		return super.findPage(page, gradeBonus);
	}
	
	@Transactional(readOnly = false)
	public void save(GradeBonus gradeBonus) {
		super.save(gradeBonus);
	}
	@Transactional(readOnly = false)
	public AjaxJson preserve(GradeBonus gradeBonus) {
		AjaxJson j = new AjaxJson();
		try{
			if (gradeBonus.getIsNewRecord()) {
				if (StringUtils.isNotBlank(gradeBonus.getGrade())) {
					GradeBonus bonus = new GradeBonus();
					bonus.setGrade(gradeBonus.getGrade());
					int count = gradeBonusMapper.count(bonus);
					if (count > 0) {
						j.setSuccess(false);
						j.setMsg("等级重复");
						return j;
					}
				}
			}else {
				if (StringUtils.isNotBlank(gradeBonus.getGrade())) {
					GradeBonus bonus = new GradeBonus();
					bonus.setGrade(gradeBonus.getGrade());
					GradeBonus grade = gradeBonusMapper.find(bonus);
					if (!grade.getId().equals(gradeBonus.getId())&&grade!=null) {
						j.setSuccess(false);
						j.setMsg("等级重复");
						return j;
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		super.save(gradeBonus);
		j.setSuccess(true);
		j.setMsg("保存等级成功");
		return j;
	}
	
	@Transactional(readOnly = false)
	public void delete(GradeBonus gradeBonus) {
		super.deleteByLogic(gradeBonus);
	}


	public List<GradeBonus> getGrade(){
	    return 	gradeBonusMapper.getGrade();
	}
	
}
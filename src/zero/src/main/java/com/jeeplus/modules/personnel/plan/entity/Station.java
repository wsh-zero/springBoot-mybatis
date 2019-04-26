/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.entity;

import com.jeeplus.modules.personnel.plan.entity.JobCategory;
import javax.validation.constraints.NotNull;

import com.jeeplus.modules.personnel.salary.entity.SallaryManager;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 岗位管理Entity
 * @author 王伟
 * @version 2019-02-18
 */
public class Station extends DataEntity<Station> {
	
	private static final long serialVersionUID = 1L;
	private String gradeNumber;		// 岗位编号
	private String name;		// 岗位名称
	private JobCategory jobType;		// 岗位类型
//	private Office depart;		// 所属部门
	private String number;
	private String duty;        //岗位职责
	private String skill;       //必备技能
	private String process;     //工作流程
	
	public Station() {
		super();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Station(String id){
		super(id);
	}

	@ExcelField(title="岗位编号", align=2, sort=1)
	public String getGradeNumber() {
		return gradeNumber;
	}

	public void setGradeNumber(String gradeNumber) {
		this.gradeNumber = gradeNumber;
	}
	
	@ExcelField(title="岗位名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="岗位类型不能为空")
	@ExcelField(title="岗位类型", fieldType=JobCategory.class, value="jobType.jobType", align=2, sort=3)
	public JobCategory getJobType() {
		return jobType;
	}

	public void setJobType(JobCategory jobType) {
		this.jobType = jobType;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}
	//	@ExcelField(title="所属部门", fieldType=Office.class, value="depart.name", align=2, sort=4)
//	public Office getDepart() {
//		return depart;
//	}
//
//	public void setDepart(Office depart) {
//		this.depart = depart;
//	}
	
}
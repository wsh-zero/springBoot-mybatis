/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.aptitude.param.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资质参数配置Entity
 * @author xy
 * @version 2019-02-22
 */
public class Aptitudeparam extends DataEntity<Aptitudeparam> {
	
	private static final long serialVersionUID = 1L;
	private String aptitudename;		// 资质名称
	private String grade;		// 级别
	
	public Aptitudeparam() {
		super();
	}

	public Aptitudeparam(String id){
		super(id);
	}

	@ExcelField(title="资质名称", align=2, sort=2)
	public String getAptitudename() {
		return aptitudename;
	}

	public void setAptitudename(String aptitudename) {
		this.aptitudename = aptitudename;
	}
	
	@ExcelField(title="级别", align=2, sort=3)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
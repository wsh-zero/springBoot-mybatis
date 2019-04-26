/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 学历管理Entity
 * @author 王伟
 * @version 2019-02-15
 */
public class Education extends DataEntity<Education> {
	
	private static final long serialVersionUID = 1L;
	private String educate;		// 学历
	
	public Education() {
		super();
	}

	public Education(String id){
		super(id);
	}

	@ExcelField(title="学历", align=2, sort=1)
	public String getEducate() {
		return educate;
	}

	public void setEducate(String educate) {
		this.educate = educate;
	}
	
}
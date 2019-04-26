/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.math.BigDecimal;

/**
 * 等级Entity
 * @author 王伟
 * @version 2019-03-14
 */
public class GradeBonus extends DataEntity<GradeBonus> {
	
	private static final long serialVersionUID = 1L;
	private String grade;		// 等级
	private Double bonus;		// 等级奖金
	
	public GradeBonus() {
		super();
	}

	public GradeBonus(String id){
		super(id);
	}

	@ExcelField(title="等级", align=2, sort=6)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@ExcelField(title="等级奖金", align=2, sort=7)
	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	
}
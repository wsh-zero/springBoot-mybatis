/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 职称Entity
 * @author 王伟
 * @version 2019-03-14
 */
public class TitleBonus extends DataEntity<TitleBonus> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 职称
	private Double bonus;		// 职称奖金
	
	public TitleBonus() {
		super();
	}

	public TitleBonus(String id){
		super(id);
	}

	@ExcelField(title="职称", align=2, sort=6)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="职称奖金", align=2, sort=7)
	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	
}
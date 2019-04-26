/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.empee.entity;

import com.jeeplus.modules.company.entity.T_Company;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工Entity
 * @author xy
 * @version 2019-01-25
 */
public class T_Empee extends DataEntity<T_Empee> {
	
	private static final long serialVersionUID = 1L;
	private T_Company company;		// 所属公司
	private String name;		// 员工姓名
	
	public T_Empee() {
		super();
	}

	public T_Empee(String id){
		super(id);
	}

	@ExcelField(title="所属公司", fieldType=T_Company.class, value="company.name", align=2, sort=1)
	public T_Company getCompany() {
		return company;
	}

	public void setCompany(T_Company company) {
		this.company = company;
	}
	
	@ExcelField(title="员工姓名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
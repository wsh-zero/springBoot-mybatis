/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.company.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 公司Entity
 * @author xy
 * @version 2019-01-25
 */
public class T_Company extends DataEntity<T_Company> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 公司名
	
	public T_Company() {
		super();
	}

	public T_Company(String id){
		super(id);
	}

	@ExcelField(title="公司名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
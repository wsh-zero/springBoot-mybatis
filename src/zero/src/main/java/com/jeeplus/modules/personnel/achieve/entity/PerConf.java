/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.achieve.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 绩效配置Entity
 * @author ww
 * @version 2019-04-08
 */
public class PerConf extends DataEntity<PerConf> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 考核标准
	
	public PerConf() {
		super();
	}

	public PerConf(String id){
		super(id);
	}

	@ExcelField(title="考核标准", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
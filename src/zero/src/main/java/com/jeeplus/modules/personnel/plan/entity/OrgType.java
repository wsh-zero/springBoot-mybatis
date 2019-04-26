/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 组织类型Entity
 * @author 王伟
 * @version 2019-02-15
 */
public class OrgType extends DataEntity<OrgType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 组织类型
	
	public OrgType() {
		super();
	}

	public OrgType(String id){
		super(id);
	}

	@ExcelField(title="组织类型", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
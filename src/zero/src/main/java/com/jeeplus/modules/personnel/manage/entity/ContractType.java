/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同类型Entity
 * @author 王伟
 * @version 2019-02-14
 */
public class ContractType extends DataEntity<ContractType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 合同类型xx
	
	public ContractType() {
		super();
	}

	public ContractType(String id){
		super(id);
	}

	@ExcelField(title="合同类型", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
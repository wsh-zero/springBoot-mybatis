/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 薪资账套设置Entity
 * @author 王伟
 * @version 2019-03-15
 */
public class SaAll extends DataEntity<SaAll> {
	
	private static final long serialVersionUID = 1L;
	private String allocation;		// 薪酬账套名称
	private String saCom;		// 薪资组成
	
	public SaAll() {
		super();
	}

	public SaAll(String id){
		super(id);
	}

	@ExcelField(title="薪酬账套名称", align=2, sort=6)
	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}
	
	@ExcelField(title="薪资组成", dictType="sa_com", align=2, sort=7)
	public String getSaCom() {
		return saCom;
	}

	public void setSaCom(String saCom) {
		this.saCom = saCom;
	}
	
}
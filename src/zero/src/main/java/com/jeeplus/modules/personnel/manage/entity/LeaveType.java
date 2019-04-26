/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 离职类型Entity
 * @author 王伟
 * @version 2019-02-14
 */
public class LeaveType extends DataEntity<LeaveType> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 离职类型
	
	public LeaveType() {
		super();
	}

	public LeaveType(String id){
		super(id);
	}

	@ExcelField(title="离职类型", align=2, sort=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
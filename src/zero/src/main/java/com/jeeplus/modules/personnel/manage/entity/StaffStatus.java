/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工状态Entity
 * @author 王伟
 * @version 2019-02-14
 */
public class StaffStatus extends DataEntity<StaffStatus> {
	
	private static final long serialVersionUID = 1L;
	private String status;		// 员工状态
	
	public StaffStatus() {
		super();
	}

	public StaffStatus(String id){
		super(id);
	}

	@ExcelField(title="员工状态", align=2, sort=1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.attendance.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 考勤类型Entity
 * @author 王伟
 * @version 2019-02-19
 */
public class AttendanceType extends DataEntity<AttendanceType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 考勤类型xx
	
	public AttendanceType() {
		super();
	}

	public AttendanceType(String id){
		super(id);
	}

	@ExcelField(title="考勤类型", align=2, sort=6)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.entity;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.personnel.salary.entity.SaAll;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工薪资配置Entity
 * @author 王伟
 * @version 2019-03-15
 */
public class StaSallary extends DataEntity<StaSallary> {
	
	private static final long serialVersionUID = 1L;
	private Staff code;		// 员工编号
	private Staff name;		// 姓名
	private StaffStatus status;		// 员工状态
	private Office depart;		// 所属部门
	private Station station;    //岗位
	private SaAll allocation;		// 薪资账套
	
	public StaSallary() {
		super();
	}

	public StaSallary(String id){
		super(id);
	}

	@ExcelField(title="员工编号", fieldType=Staff.class, value="code.code", align=2, sort=6)
	public Staff getCode() {
		return code;
	}

	public void setCode(Staff code) {
		this.code = code;
	}
	
	@ExcelField(title="姓名", fieldType=Staff.class, value="name.name", align=2, sort=7)
	public Staff getName() {
		return name;
	}

	public void setName(Staff name) {
		this.name = name;
	}
	
	@ExcelField(title="员工状态", fieldType=StaffStatus.class, value="status.status", align=2, sort=8)
	public StaffStatus getStatus() {
		return status;
	}

	public void setStatus(StaffStatus status) {
		this.status = status;
	}
	
	@ExcelField(title="所属部门", fieldType=Office.class, value="depart.name", align=2, sort=9)
	public Office getDepart() {
		return depart;
	}

	public void setDepart(Office depart) {
		this.depart = depart;
	}
	@ExcelField(title="岗位", fieldType=Station.class, value="station.name", align=2, sort=10)
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@ExcelField(title="薪资账套", fieldType=SaAll.class, value="account.allocation", align=2, sort=11)
	public SaAll getAllocation() {
		return allocation;
	}

	public void setAllocation(SaAll allocation) {
		this.allocation = allocation;
	}
	
}
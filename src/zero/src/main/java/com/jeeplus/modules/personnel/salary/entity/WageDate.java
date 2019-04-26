/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工资发放时间设置Entity
 * @author 王伟
 * @version 2019-03-20
 */
public class WageDate extends DataEntity<WageDate> {
	
	private static final long serialVersionUID = 1L;
	private Integer wageDate;		// 工资发放时间
	private Integer slipeDate;		// 工资条生成时间
	private String scheduleJob;     //
	
	public WageDate() {
		super();
	}

	public WageDate(String id){
		super(id);
	}

	@NotNull(message="工资发放时间不能为空")
	@ExcelField(title="工资发放时间", align=2, sort=7)
	public Integer getWageDate() {
		return wageDate;
	}

	public void setWageDate(Integer wageDate) {
		this.wageDate = wageDate;
	}
	
	@NotNull(message="工资条生成时间不能为空")
	@ExcelField(title="工资条生成时间", align=2, sort=8)
	public Integer getSlipeDate() {
		return slipeDate;
	}

	public void setSlipeDate(Integer slipeDate) {
		this.slipeDate = slipeDate;
	}

	public String getScheduleJob() {
		return scheduleJob;
	}

	public void setScheduleJob(String scheduleJob) {
		this.scheduleJob = scheduleJob;
	}
}
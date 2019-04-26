/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.activiti.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 请假申请Entity
 * @author xy
 * @version 2019-01-28
 */
public class T_OA_Leave extends ActEntity<T_OA_Leave> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例id
	private String leaveType;		// 请假类型
	private Date startTime;		// 请假开始时间
	private Date endTime;		// 请假结束时间
	private String reason;		// 请假理由
	
	public T_OA_Leave() {
		super();
	}

	public T_OA_Leave(String id){
		super(id);
	}

	@ExcelField(title="流程实例id", align=2, sort=7)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@ExcelField(title="请假类型", dictType="oa_leave_type", align=2, sort=8)
	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="请假开始时间", align=2, sort=9)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="请假结束时间", align=2, sort=10)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="请假理由", align=2, sort=11)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
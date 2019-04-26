/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.gallant.entity;


import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 招骋需求Entity
 * @author xy
 * @version 2019-01-30
 */
public class Gallantapplication extends ActEntity<Gallantapplication> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例id
	private String appno;		// 申请编号
	private String company;		// 所属公司
	private String department;		// 所属部门
	private String postname;		// 岗位名称
	private String postnum;		// 岗位数量
	private String completenum;		// 已完成数量
	private String postdetail;		// 岗位详情
	private String operator;		// 操作人
	private Date operatime;		// 操作时间
	private String general;  //总经办操作人
	private Date generaltime;  //总经办操作时间
	private String hrname;    //HR姓名
	private Date hrtime;    //HR完成时间
	private String type;		// 操作分类
	private String status;		// 操作状态
	private String gallantstatus;		// 招骋状态
	
	public Gallantapplication() {
		super();
	}

	public Gallantapplication(String id){
		super(id);
	}

	@ExcelField(title="流程实例id", align=2, sort=7)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@ExcelField(title="申请编号", align=2, sort=8)
	public String getAppno() {
		return appno;
	}

	public void setAppno(String appno) {
		this.appno = appno;
	}
	
	@ExcelField(title="所属公司", align=2, sort=9)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@ExcelField(title="所属部门", align=2, sort=10)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@ExcelField(title="岗位名称", align=2, sort=11)
	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}
	
	@ExcelField(title="岗位数量", align=2, sort=12)
	public String getPostnum() {
		return postnum;
	}

	public void setPostnum(String postnum) {
		this.postnum = postnum;
	}
	
	@ExcelField(title="已完成数量", align=2, sort=13)
	public String getCompletenum() {
		return completenum;
	}

	public void setCompletenum(String completenum) {
		this.completenum = completenum;
	}
	
	@ExcelField(title="岗位详情", align=2, sort=14)
	public String getPostdetail() {
		return postdetail;
	}

	public void setPostdetail(String postdetail) {
		this.postdetail = postdetail;
	}
	
	@ExcelField(title="操作人", align=2, sort=15)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@ExcelField(title="操作时间", align=2, sort=16)
	public Date getOperatime() {
		return operatime;
	}

	public void setOperatime(Date operatime) {
		this.operatime = operatime;
	}
	
	@ExcelField(title="操作分类", align=2, sort=17)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="操作状态", dictType="app_status", align=2, sort=18)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="招骋状态", align=2, sort=19)
	public String getGallantstatus() {
		return gallantstatus;
	}

	public void setGallantstatus(String gallantstatus) {
		this.gallantstatus = gallantstatus;
	}


	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public Date getGeneraltime() {
		return generaltime;
	}

	public void setGeneraltime(Date generaltime) {
		this.generaltime = generaltime;
	}

	public String getHrname() {
		return hrname;
	}

	public void setHrname(String hrname) {
		this.hrname = hrname;
	}

	public Date getHrtime() {
		return hrtime;
	}

	public void setHrtime(Date hrtime) {
		this.hrtime = hrtime;
	}
}
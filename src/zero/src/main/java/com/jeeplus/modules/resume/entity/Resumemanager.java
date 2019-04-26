/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resume.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import groovy.transform.ToString;

/**
 * 简历管理Entity
 * @author xy
 * @version 2019-02-14
 */
@ToString
public class Resumemanager extends DataEntity<Resumemanager> {
	
	private static final long serialVersionUID = 1L;
	private String resumeno;		// 简历编号
	private String name;		// 求职者姓名
	private String sex;		// 性别
	private String telphone;		// 手机号码
	private String deptno;		// 应聘部门
	private String interviewer;		// 面试官
	private Date auditiontime;		// 面试时间
	private String record;		// 面试记录
	private String result;		// 面试结果
	private String status;		// 简历状态
	private String flag;   //标志
	
	public Resumemanager() {
		super();
	}

	public Resumemanager(String id){
		super(id);
	}

	@ExcelField(title="简历编号", align=2, sort=7)
	public String getResumeno() {
		return resumeno;
	}

	public void setResumeno(String resumeno) {
		this.resumeno = resumeno;
	}
	
	@ExcelField(title="求职者姓名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="性别", align=2, sort=9)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@ExcelField(title="手机号码", align=2, sort=10)
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
	@ExcelField(title="应聘部门", dictType="", align=2, sort=11)
	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}
	
	@ExcelField(title="面试官", dictType="", align=2, sort=12)
	public String getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="面试时间", align=2, sort=13)
	public Date getAuditiontime() {
		return auditiontime;
	}

	public void setAuditiontime(Date auditiontime) {
		this.auditiontime = auditiontime;
	}
	
	@ExcelField(title="面试记录", align=2, sort=14)
	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}
	
	@ExcelField(title="面试结果", align=2, sort=15)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@ExcelField(title="简历状态", align=2, sort=16)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.aptitude.cooper.entity;

import com.jeeplus.modules.aptitude.param.entity.Aptitudeparam;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合作个人资质管理Entity
 * @author xy
 * @version 2019-02-22
 */
public class Cooperaptitude extends DataEntity<Cooperaptitude> {
	
	private static final long serialVersionUID = 1L;
	private String aptitudeno;		// 资质编号
	private Aptitudeparam aptitudename;		//  资质名称
	private String issueunit;		// 颁发单位
	private Date issuetime;		// 颁发日期
	private String validtime;		// 有效期限
	private String scanning;		// 资质扫描件
	private String certificate;		// 证书持有人
	private String grade;		// 等级
	private Date expiretime;		// 到期日期
	
	public Cooperaptitude() {
		super();
	}

	public Cooperaptitude(String id){
		super(id);
	}

	@ExcelField(title="资质编号", align=2, sort=7)
	public String getAptitudeno() {
		return aptitudeno;
	}

	public void setAptitudeno(String aptitudeno) {
		this.aptitudeno = aptitudeno;
	}
	
	@NotNull(message=" 资质名称不能为空")
	@ExcelField(title=" 资质名称", fieldType=Aptitudeparam.class, value="aptitudename.aptitudename", align=2, sort=8)
	public Aptitudeparam getAptitudename() {
		return aptitudename;
	}

	public void setAptitudename(Aptitudeparam aptitudename) {
		this.aptitudename = aptitudename;
	}
	
	@ExcelField(title="颁发单位", align=2, sort=9)
	public String getIssueunit() {
		return issueunit;
	}

	public void setIssueunit(String issueunit) {
		this.issueunit = issueunit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="颁发日期不能为空")
	@ExcelField(title="颁发日期", align=2, sort=10)
	public Date getIssuetime() {
		return issuetime;
	}

	public void setIssuetime(Date issuetime) {
		this.issuetime = issuetime;
	}
	
	@NotNull(message="有效期限不能为空")
	@ExcelField(title="有效期限", align=2, sort=11)
	public String getValidtime() {
		return validtime;
	}

	public void setValidtime(String validtime) {
		this.validtime = validtime;
	}
	
	@ExcelField(title="资质扫描件", align=2, sort=12)
	public String getScanning() {
		return scanning;
	}

	public void setScanning(String scanning) {
		this.scanning = scanning;
	}
	
	@ExcelField(title="证书持有人", align=2, sort=13)
	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	@ExcelField(title="等级", align=2, sort=14)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="到期日期", align=2, sort=15)
	public Date getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(Date expiretime) {
		this.expiretime = expiretime;
	}
	
}
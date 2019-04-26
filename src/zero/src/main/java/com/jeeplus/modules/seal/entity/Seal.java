/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.seal.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 使用印章管理Entity
 * @author xy
 * @version 2019-02-22
 */
public class Seal extends ActEntity<Seal> {
	
	private static final long serialVersionUID = 1L;
	private String appperson;		// 申请人
	private String purpose;		// 用途
	private String filename;		// 文件名称
	private String sealtype;		// 用章类型
	private Date sealtime;		// 盖章时间
	private String sealperson;		// 盖章人
	private String procInsId;		// 流程实例id
	
	public Seal() {
		super();
	}

	public Seal(String id){
		super(id);
	}

	@ExcelField(title="申请人", align=2, sort=6)
	public String getAppperson() {
		return appperson;
	}

	public void setAppperson(String appperson) {
		this.appperson = appperson;
	}
	
	@ExcelField(title="用途", align=2, sort=7)
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	@ExcelField(title="文件名称", dictType="", align=2, sort=8)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@ExcelField(title="用章类型", dictType="", align=2, sort=9)
	public String getSealtype() {
		return sealtype;
	}

	public void setSealtype(String sealtype) {
		this.sealtype = sealtype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="盖章时间不能为空")
	@ExcelField(title="盖章时间", align=2, sort=10)
	public Date getSealtime() {
		return sealtime;
	}

	public void setSealtime(Date sealtime) {
		this.sealtime = sealtime;
	}
	
	@ExcelField(title="盖章人", align=2, sort=11)
	public String getSealperson() {
		return sealperson;
	}

	public void setSealperson(String sealperson) {
		this.sealperson = sealperson;
	}
	
	@ExcelField(title="流程实例id", align=2, sort=12)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
}
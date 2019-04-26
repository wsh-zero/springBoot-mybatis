/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.external.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 公司对外公文管理Entity
 * @author xy
 * @version 2019-02-25
 */
public class Externalofficial extends ActEntity<Externalofficial> {
	
	private static final long serialVersionUID = 1L;
	private String externo;		// 公文编号
	private String extername;		// 公文名称
	private Date publishtime;		// 发布时间
	private String publishperson;		// 发布人
	private String publishdepart;		// 发布部门
	private String appendix;		// 附件
	private String receive;		// 接收人/单位
	private String procInsId;		// 流程实例id
	private String publishDepartName; // 发布部门名称

	@ExcelField(title="备注", align=2, sort=15)
	@Override
	public String getRemarks() {
		return super.getRemarks();
	}

	@Override
	public void setRemarks(String remarks) {
		super.setRemarks(remarks);
	}
	public Externalofficial() {
		super();
	}

	public Externalofficial(String id){
		super(id);
	}

	@ExcelField(title="公文编号", align=2, sort=7)
	public String getExterno() {
		return externo;
	}

	public void setExterno(String externo) {
		this.externo = externo;
	}
	
	@ExcelField(title="公文名称", align=2, sort=8)
	public String getExtername() {
		return extername;
	}

	public void setExtername(String extername) {
		this.extername = extername;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发布时间", align=2, sort=9)
	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}
	
	@ExcelField(title="发布人", align=2, sort=10)
	public String getPublishperson() {
		return publishperson;
	}

	public void setPublishperson(String publishperson) {
		this.publishperson = publishperson;
	}
	
	@ExcelField(title="发布部门", fieldType=String.class, value="", align=2, sort=11)
	public String getPublishdepart() {
		return publishdepart;
	}

	public void setPublishdepart(String publishdepart) {
		this.publishdepart = publishdepart;
	}
	
	@ExcelField(title="附件", align=2, sort=12)
	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	
	@ExcelField(title="接收人/单位", align=2, sort=13)
	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}
	
	@ExcelField(title="流程实例id", align=2, sort=14)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getPublishDepartName() {
		return publishDepartName;
	}

	public void setPublishDepartName(String publishDepartName) {
		this.publishDepartName = publishDepartName;
	}
}
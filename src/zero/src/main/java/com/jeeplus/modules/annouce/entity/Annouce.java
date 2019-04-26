/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.annouce.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.sys.entity.User;
import groovy.transform.ToString;

/**
 * 发布公告管理Entity
 * @author xy
 * @version 2019-02-28
 */
@ToString
public class Annouce extends DataEntity<Annouce> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private String appendix;		// 附件
	private String status;		// 状态
	private Date startTime;		// 开始日期
	private Date endTime;		// 结束日期
	private String receivePerson;		// 接收人
	private String publishPerson;		// 发布人
	private Date publishTime;		// 发布时间
	private String type;		// 类型
	private String readNum;		// 已读
	private String unReadNum;	// 未读
	private boolean isSelf;		// 是否只查询自己的通知
	private String readFlag;	// 本人阅读状态
	private List<AnnouceRecord> annouceRecordList = Lists.newArrayList();
	private String number;
	private String readState;
	
	public Annouce() {
		super();
	}

	public Annouce(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="附件", align=2, sort=3)
	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	
	@ExcelField(title="状态", dictType="oa_notify_type", align=2, sort=4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始日期不能为空")
	@ExcelField(title="开始日期", align=2, sort=5)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束日期不能为空")
	@ExcelField(title="结束日期", align=2, sort=6)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getReceivePersons() {
		return Collections3.extractToString(annouceRecordList, "user.id", ",");
	}

	public void setReceivePersons(String receivePerson) {
		this.annouceRecordList = Lists.newArrayList();
		for (String id : StringUtils.split(receivePerson, ",")){
			AnnouceRecord entity = new AnnouceRecord();
			entity.setId(IdGen.uuid());
			entity.setAnnouce(this);
			entity.setUser(new User(id));
			entity.setReadFlag("0");
			this.annouceRecordList.add(entity);
		}
	}

	@ExcelField(title="发布人", align=2, sort=8)
	public String getPublishPerson() {
		return publishPerson;
	}

	public void setPublishPerson(String publishPerson) {
		this.publishPerson=publishPerson;
	}

	/**
	 * 获取通知发送记录用户Name
	 * @return
	 */
	public String getAnnouceRecordNames() {
		return Collections3.extractToString(annouceRecordList, "user.name", ",") ;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发布时间", align=2, sort=9)
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	@ExcelField(title="类型", dictType="", align=2, sort=10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public String getUnReadNum() {
		return unReadNum;
	}

	public void setUnReadNum(String unReadNum) {
		this.unReadNum = unReadNum;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean self) {
		isSelf = self;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public List<AnnouceRecord> getAnnouceRecordList() {
		return annouceRecordList;
	}

	public void setAnnouceRecordList(List<AnnouceRecord> annouceRecordList) {
		this.annouceRecordList = annouceRecordList;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getReadState() {
		return readState;
	}

	public void setReadState(String readState) {
		this.readState = readState;
	}
}
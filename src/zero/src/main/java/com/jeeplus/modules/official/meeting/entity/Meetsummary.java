/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.meeting.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.annouce.entity.AnnouceRecord;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会议纪要管理Entity
 * @author xy
 * @version 2019-03-04
 */
public class Meetsummary extends DataEntity<Meetsummary> {
	
	private static final long serialVersionUID = 1L;
	private String meettitle;		// 会议主题
	private String meetcontent;		// 会议内容
	private String appendix;		// 附件
	private String status;		// 状态
	private Date startTime;		// 开始日期
	private Date endTime;		// 结束日期
	private User meetperson;		// 参会人
	private String inviteperson;		// 发起人
	private Date invitedate;    //发起时间
	private String meettype;		// 会议类型

	private String readNum;		// 已读
	private String unReadNum;	// 未读

	private boolean isSelf;		// 是否只查询自己的通知

	private String readFlag;	// 本人阅读状态

	private List<MeetsummmaryRecord> meetsummmaryRecordList = Lists.newArrayList();
	
	public Meetsummary() {
		super();
	}

	public Meetsummary(String id){
		super(id);
	}


	public String getReceivePersons() {
		return Collections3.extractToString(meetsummmaryRecordList, "user.id", ",");
	}

	public void setReceivePersons(String meetperson) {
		this.meetsummmaryRecordList = Lists.newArrayList();
		for (String id : StringUtils.split(meetperson, ",")){
			MeetsummmaryRecord entity = new MeetsummmaryRecord();
			entity.setId(IdGen.uuid());
			entity.setMeetsummary(this);
			entity.setUser(new User(id));
			entity.setReadFlag("0");
			this.meetsummmaryRecordList.add(entity);
		}
	}

	/**
	 * 获取通知发送记录用户Name
	 * @return
	 */
	public String getMeetRecordNames() {
		return Collections3.extractToString(meetsummmaryRecordList, "user.name", ",") ;
	}


	@ExcelField(title="会议主题", align=2, sort=3)
	public String getMeettitle() {
		return meettitle;
	}

	public void setMeettitle(String meettitle) {
		this.meettitle = meettitle;
	}
	
	@ExcelField(title="会议内容", align=2, sort=4)
	public String getMeetcontent() {
		return meetcontent;
	}

	public void setMeetcontent(String meetcontent) {
		this.meetcontent = meetcontent;
	}
	
	@ExcelField(title="附件", align=2, sort=5)
	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	
	@ExcelField(title="状态", dictType="", align=2, sort=6)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始日期不能为空")
	@ExcelField(title="开始日期", align=2, sort=7)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束日期不能为空")
	@ExcelField(title="结束日期", align=2, sort=8)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@ExcelField(title="参会人", fieldType=User.class, value="meetperson.name", align=2, sort=9)
	public User getMeetperson() {
		return meetperson;
	}

	public void setMeetperson(User meetperson) {
		this.meetperson = meetperson;
	}
	
	@ExcelField(title="发起人", align=2, sort=10)
	public String getInviteperson() {
		return inviteperson;
	}

	public void setInviteperson(String inviteperson) {
		this.inviteperson = inviteperson;
	}
	
	@ExcelField(title="会议类型", dictType="", align=2, sort=11)
	public String getMeettype() {
		return meettype;
	}

	public void setMeettype(String meettype) {
		this.meettype = meettype;
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

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public List<MeetsummmaryRecord> getMeetsummmaryRecordList() {
		return meetsummmaryRecordList;
	}

	public void setMeetsummmaryRecordList(List<MeetsummmaryRecord> meetsummmaryRecordList) {
		this.meetsummmaryRecordList = meetsummmaryRecordList;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发布时间", align=2, sort=9)
	public Date getInvitedate() {
		return invitedate;
	}

	public void setInvitedate(Date invitedate) {
		this.invitedate = invitedate;
	}
}
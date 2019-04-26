/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.train.entity;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.personnel.train.entity.TrainType;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.User;

/**
 * 培训管理Entity
 * @author 王伟
 * @version 2019-02-19
 */
public class Train extends DataEntity<Train> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 主题
	private TrainType type;		// 培训类型
	private String content;		// 内容
	private Staff initiator;		// 培训发起人
	private Date startTime;		// 培训开始时间
	private Date endTime;		// 培训结束时间
	private String file;		// 附件
	private Double cost;        //费用
	private String venue;       //培训地点
	private String readNum;		// 已读
	private String unReadNum;	// 未读
	private String status;		// 状态
	private String qualifyNum;  //合格
	private String unQualifyNum;//不合格
	private String time;        //培训时长

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private boolean isSelf;		// 是否只查询自己的通知

	private String readFlag;	// 本人阅读状态

	private String qualifyFlag; //本人合格状态

	private Date createDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getCreateDate() {
		return createDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	private List<TrainRecord> trainRecordList = Lists.newArrayList();

	public String getQualifyFlag() {
		return qualifyFlag;
	}

	public void setQualifyFlag(String qualifyFlag) {
		this.qualifyFlag = qualifyFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Train() {
		super();
	}

	public Train(String id){
		super(id);
	}

	@ExcelField(title="主题", align=2, sort=7)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="培训类型", fieldType=TrainType.class, value="type.name", align=2, sort=8)
	public TrainType getType() {
		return type;
	}

	public void setType(TrainType type) {
		this.type = type;
	}
	
	@ExcelField(title="内容", align=2, sort=9)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="培训发起人", fieldType=Staff.class, value="initiator.name", align=2, sort=10)
	public Staff getInitiator() {
		return initiator;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getQualifyNum() {
		return qualifyNum;
	}

	public void setQualifyNum(String qualifyNum) {
		this.qualifyNum = qualifyNum;
	}

	public String getUnQualifyNum() {
		return unQualifyNum;
	}

	public void setUnQualifyNum(String unQualifyNum) {
		this.unQualifyNum = unQualifyNum;
	}

	public void setInitiator(Staff initiator) {
		this.initiator = initiator;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@ExcelField(title="培训开始时间", align=2, sort=11)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@ExcelField(title="培训结束时间", align=2, sort=12)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="附件", align=2, sort=13)
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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

	public List<TrainRecord> getTrainRecordList() {
		return trainRecordList;
	}

	public void setTrainRecordList(List<TrainRecord> trainRecordList) {
		this.trainRecordList = trainRecordList;
	}

	/**
	 * 获取通知发送记录用户ID
	 * @return
	 */
	public String getTrainRecordIds() {
		return Collections3.extractToString(trainRecordList, "user.id", ",") ;
	}

	/**
	 * 设置通知发送记录用户ID
	 * @return
	 */
	public void setTrainRecordIds(String trainRecord) {
		this.trainRecordList = Lists.newArrayList();
		for (String id : StringUtils.split(trainRecord, ",")){
			TrainRecord entity = new TrainRecord();
			entity.setId(IdGen.uuid());
			entity.setTrain(this);
			entity.setUser(new User(id));
			entity.setReadFlag("0");
			this.trainRecordList.add(entity);
		}
	}

	/**
	 * 获取通知发送记录用户Name
	 * @return
	 */
	public String getTrainRecordNames() {
		return Collections3.extractToString(trainRecordList, "user.name", ",") ;
	}

	/**
	 * 设置通知发送记录用户Name
	 * @return
	 */
	public void setTrainRecordNames(String trainRecord) {
		// 什么也不做
	}


}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.internal.entity;

import com.jeeplus.common.utils.Collections3;
import com.jeeplus.modules.official.internalrecord.entity.InternalOfficalRecord;
import com.jeeplus.modules.official.type.entity.OfficialType;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.User;

/**
 * 公司对内公文管理Entity
 * @author chentao
 * @version 2019-04-03
 */
public class InternalOfficial extends ActEntity<InternalOfficial> {
	
	private static final long serialVersionUID = 1L;
	private OfficialType officialType;		// 公文类型
	private String internalno;		// 公文编号
	private String internalname;		// 公文名称
	private Date publishtime;		// 发布时间
	private String publishperson;		// 发布人
	private String publishdepart;		// 发布部门
	private String appendix;		// 附件
	private String procInsId;		// 流程实例id

	private String targetUsers;
	private boolean isSelf;
	/**
	 * 接收人
	 */
	private List<InternalOfficalRecord> targets = new ArrayList<>();
	
	public InternalOfficial() {

		super();
	}

	public InternalOfficial(String id){
		super(id);
	}

	@NotNull(message="公文类型不能为空")
	@ExcelField(title="公文类型", fieldType=OfficialType.class, value="officialType.typename", align=2, sort=3)
	public OfficialType getOfficialType() {
		return officialType;
	}

	public void setOfficialType(OfficialType officialType) {
		this.officialType = officialType;
	}
	
	@ExcelField(title="公文编号", align=2, sort=4)
	public String getInternalno() {
		return internalno;
	}

	public void setInternalno(String internalno) {
		this.internalno = internalno;
	}
	
	@ExcelField(title="公文名称", align=2, sort=5)
	public String getInternalname() {
		return internalname;
	}

	public void setInternalname(String internalname) {
		this.internalname = internalname;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发布时间", align=2, sort=6)
	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}
	
	@ExcelField(title="发布人", align=2, sort=7)
	public String getPublishperson() {
		return publishperson;
	}

	public void setPublishperson(String publishperson) {
		this.publishperson = publishperson;
	}
	
	@ExcelField(title="发布部门", fieldType=String.class, value="", align=2, sort=8)
	public String getPublishdepart() {
		return publishdepart;
	}

	public void setPublishdepart(String publishdepart) {
		this.publishdepart = publishdepart;
	}
	
	@ExcelField(title="附件", align=2, sort=9)
	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	
	@ExcelField(title="流程实例id", align=2, sort=10)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getTargetUsers() {
		return targets != null ? Collections3.extractToString(targets, "userId.name", ",") : "";
	}

	public void setTargetUsers(String targetUsers) {
		this.targetUsers = targetUsers;
		String[] ids = targetUsers.split(",");
		List<InternalOfficalRecord> records = new ArrayList<InternalOfficalRecord>();
		for (String str : ids) {
			InternalOfficalRecord record = new InternalOfficalRecord();
			record.setId(UUID.randomUUID().toString());
			User user = new User();
			user.setId(str);
			record.setUserId(user);
			record.setReaded("0");
			record.setReadTime(new Date());
			records.add(record);
		}
		setTargets(records);
	}

	public List<InternalOfficalRecord> getTargets() {
		return targets;
	}

	public void setTargets(List<InternalOfficalRecord> targets) {
		this.targets = targets;
	}

	public boolean getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(boolean self) {
		isSelf = self;
	}
}
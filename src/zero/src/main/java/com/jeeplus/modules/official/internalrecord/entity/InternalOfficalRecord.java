/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.internalrecord.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.User;
import java.util.Date;

/**
 * 内部公文接收状态Entity
 * @author chentao
 * @version 2019-04-03
 */
public class InternalOfficalRecord extends DataEntity<InternalOfficalRecord> {
	
	private static final long serialVersionUID = 1L;
	private String readed;		// 是否已读
	private Date readTime;		// 读取时间
	private String officalId;		// 主表外键
	private User userId;		// 用户id
	
	public InternalOfficalRecord() {
		super();
	}

	public InternalOfficalRecord(String id){
		super(id);
	}

	@ExcelField(title="是否已读", align=2, sort=2)
	public String getReaded() {
		return readed;
	}

	public void setReaded(String readed) {
		this.readed = readed;
	}
	
	@ExcelField(title="读取时间", align=2, sort=3)
	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	@ExcelField(title="主表外键", align=2, sort=4)
	public String getOfficalId() {
		return officalId;
	}

	public void setOfficalId(String officalId) {
		this.officalId = officalId;
	}

	@ExcelField(title="用户id", align=2, sort=5)
	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

}
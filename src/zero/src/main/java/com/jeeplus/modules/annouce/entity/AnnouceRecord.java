/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.annouce.entity;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import groovy.transform.ToString;

import java.util.Date;

/**
 * 阅读公告管理Entity
 * @author xy
 * @version 2019-02-28
 */
@ToString
public class AnnouceRecord extends DataEntity<AnnouceRecord> {
	
	private static final long serialVersionUID = 1L;
	private Annouce annouce;		// 告公主键
	private User user;		// 用户主键
	private String readFlag;		// 是否阅读
	private Date readDate;		// 阅读时间
	
	public AnnouceRecord() {
		super();
	}

	public AnnouceRecord(String id){
		super(id);
	}

	public AnnouceRecord(Annouce annouce){
		this.annouce=annouce;
	}

	@ExcelField(title="告公主键", fieldType=Annouce.class, value="annouce.title", align=2, sort=1)
	public Annouce getAnnouce() {
		return annouce;
	}

	public void setAnnouce(Annouce annouce) {
		this.annouce = annouce;
	}

	@ExcelField(title="用户主键", fieldType=User.class, value="user.name", align=2, sort=2)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
}
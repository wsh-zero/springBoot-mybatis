/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.entity;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 行政处罚记录Entity
 * @author 王伟
 * @version 2019-03-19
 */
public class PenaltyRecord extends DataEntity<PenaltyRecord> {
	
	private static final long serialVersionUID = 1L;
	private Staff code;		// 员工编号
	private User name;		// 姓名
	private Date punishDate;		// 处罚日期
	private String punishType;		// 处罚类型
	private String punishAmount;		// 罚款金额
	private Date beginPunishDate;		// 开始 处罚日期
	private Date endPunishDate;		// 结束 处罚日期
	
	public PenaltyRecord() {
		super();
	}

	public PenaltyRecord(String id){
		super(id);
	}

	@ExcelField(title="员工编号", fieldType=Staff.class, value="code.code", align=2, sort=6)
	public Staff getCode() {
		return code;
	}

	public void setCode(Staff code) {
		this.code = code;
	}
	
	@NotNull(message="姓名不能为空")
	@ExcelField(title="姓名", fieldType=User.class, value="name.name", align=2, sort=7)
	public User getName() {
		return name;
	}

	public void setName(User name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="处罚日期不能为空")
	@ExcelField(title="处罚日期", align=2, sort=8)
	public Date getPunishDate() {
		return punishDate;
	}

	public void setPunishDate(Date punishDate) {
		this.punishDate = punishDate;
	}
	
	@ExcelField(title="处罚类型", dictType="admin_punish_type", align=2, sort=9)
	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}
	
	@ExcelField(title="罚款金额", align=2, sort=10)
	public String getPunishAmount() {
		return punishAmount;
	}

	public void setPunishAmount(String punishAmount) {
		this.punishAmount = punishAmount;
	}
	
	public Date getBeginPunishDate() {
		return beginPunishDate;
	}

	public void setBeginPunishDate(Date beginPunishDate) {
		this.beginPunishDate = beginPunishDate;
	}
	
	public Date getEndPunishDate() {
		return endPunishDate;
	}

	public void setEndPunishDate(Date endPunishDate) {
		this.endPunishDate = endPunishDate;
	}
		
}
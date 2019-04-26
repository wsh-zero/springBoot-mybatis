/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.entity;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.personnel.plan.entity.Org;
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Email;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工通讯录Entity
 * @author 王伟
 * @version 2019-01-30
 */
public class Contact extends DataEntity<Contact> {
	
	private static final long serialVersionUID = 1L;
	private String staffId;		// 员工id
	private Staff name;		// 姓名
	private Office company;		// 公司
	private Office depart;		// 所属部门
	private Staff staffCode;		// 员工编号
	private Station station;		// 岗位
	private Staff contactType;		// 联系方式
	private String email;		// 个人邮箱
	private String qq;		// QQ号码
	private String weChat;		// 微信
	private String status;		// 状态
	
	public Contact() {
		super();
	}

	public Contact(String id){
		super(id);
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	@NotNull(message="姓名不能为空")
	@ExcelField(title="姓名", fieldType=Staff.class, value="name.name", align=2, sort=1)
	public Staff getName() {
		return name;
	}

	public void setName(Staff name) {
		this.name = name;
	}
	
	@ExcelField(title="公司", fieldType=Org.class, value="company.name", align=2, sort=2)
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}
	
	@ExcelField(title="所属部门", fieldType=Org.class, value="depart.name", align=2, sort=3)
	public Office getDepart() {
		return depart;
	}

	public void setDepart(Office depart) {
		this.depart = depart;
	}
	
	@ExcelField(title="员工编号(必填)", fieldType=Staff.class, value="staffCode", align=2, sort=4)
	public Staff getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(Staff staffCode) {
		this.staffCode = staffCode;
	}
	
	@ExcelField(title="岗位", fieldType=Station.class, value="station.name", align=2, sort=5)
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
	@ExcelField(title="联系方式", fieldType=Staff.class, value="contactType.contactType", align=2, sort=6)
	public Staff getContactType() {
		return contactType;
	}

	public void setContactType(Staff contactType) {
		this.contactType = contactType;
	}
	
	@Email(message="个人邮箱必须为合法邮箱")
	@ExcelField(title="个人邮箱（必填）", align=2, sort=7)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	@ExcelField(title="QQ号码（必填）", align=2, sort=9)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@ExcelField(title="微信（必填）", align=2, sort=10)
	public String getWeChat() {
		return weChat;
	}

	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}
	
	@ExcelField(title="状态(启用，禁用)", dictType="usage_state", align=2, sort=11)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.asset.getstore.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.utils.Collections3;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.User;

/**
 * 固定资产出库管理Entity
 * @author xy
 * @version 2019-02-22
 */
public class Getstoreasset extends DataEntity<Getstoreasset> {

	private static final long serialVersionUID = 1L;
	private String assetname;        // 资产名称
	private String specimodel;        // 规格/型号
	private Date buytime;        // 购买时间
	private Double amount;        // 金额
	private Integer number;        // 数量
	private String address;        // 存放地点
	private String useperson;        // 使用人
	private String usestatus;        // 使用状态
	private String personliable;        // 责任人
	private Integer yearnum;        // 财务折旧年限（年）
	private Double assetvalue;        // 资产净值

	private String psersonlianame;  //责任人回显使用

	private List<User> userpersonlist = new ArrayList<>();


	public String getPsersonlianame() {
		return psersonlianame;
	}

	public void setPsersonlianame(String psersonlianame) {
		this.psersonlianame = psersonlianame;
	}

	public Getstoreasset() {
		super();
	}

	public Getstoreasset(String id) {
		super(id);
	}

	@ExcelField(title = "资产名称", align = 2, sort = 7)
	public String getAssetname() {
		return assetname;
	}

	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}

	@ExcelField(title = "规格/型号", align = 2, sort = 8)
	public String getSpecimodel() {
		return specimodel;
	}

	public void setSpecimodel(String specimodel) {
		this.specimodel = specimodel;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "购买时间不能为空")
	@ExcelField(title = "购买时间", align = 2, sort = 9)
	public Date getBuytime() {
		return buytime;
	}

	public void setBuytime(Date buytime) {
		this.buytime = buytime;
	}

	@NotNull(message = "金额不能为空")
	@ExcelField(title = "金额", align = 2, sort = 10)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@NotNull(message = "数量不能为空")
	@ExcelField(title = "数量", align = 2, sort = 11)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@ExcelField(title = "存放地点", align = 2, sort = 12)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title = "使用人", fieldType = String.class, value = "", align = 2, sort = 13)
	public String getUseperson() {
		return useperson;
	}

	public void setUseperson(String useperson) {
		this.useperson = useperson;
	}

	public String getUserpersonIds() {
		return Collections3.extractToString(userpersonlist, "id", ",");
	}

	public String getUserpersonNames() {
		return Collections3.extractToString(userpersonlist, "name", ",");
	}

	@ExcelField(title = "使用状态", dictType = "", align = 2, sort = 14)
	public String getUsestatus() {
		return usestatus;
	}

	public void setUsestatus(String usestatus) {
		this.usestatus = usestatus;
	}

	@ExcelField(title = "责任人", fieldType = String.class, value = "", align = 2, sort = 15)
	public String getPersonliable() {
		return personliable;
	}

	public void setPersonliable(String personliable) {
		this.personliable = personliable;
	}

	@NotNull(message = "财务折旧年限（年）不能为空")
	@ExcelField(title = "财务折旧年限（年）", align = 2, sort = 16)
	public Integer getYearnum() {
		return yearnum;
	}

	public void setYearnum(Integer yearnum) {
		this.yearnum = yearnum;
	}

	@NotNull(message = "资产净值不能为空")
	@ExcelField(title = "资产净值", align = 2, sort = 17)
	public Double getAssetvalue() {
		return assetvalue;
	}

	public void setAssetvalue(Double assetvalue) {
		this.assetvalue = assetvalue;
	}

	public List<User> getUserpersonlist() {
		return userpersonlist;
	}

	public void setUserpersonlist(List<User> userpersonlist) {
		this.userpersonlist = userpersonlist;
	}
}
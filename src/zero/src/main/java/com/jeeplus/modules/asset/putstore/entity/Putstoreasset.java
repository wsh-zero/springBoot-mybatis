/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.asset.putstore.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 固定资产入库管理Entity
 * @author xy
 * @version 2019-02-22
 */
public class Putstoreasset extends DataEntity<Putstoreasset> {
	
	private static final long serialVersionUID = 1L;
	private String assetname;		// 资产名称
	private String specimodel;		// 规格/型号
	private Date buytime;		// 购买日期
	private Date putassettime;		// 入库日期
	private Double amount;		// 金额
	private Integer number;		// 数量
	private String address;		// 存入地点
	private Integer yearnum;		// 财务折旧年限（年）
	private Double assetvalue;		// 资产净值
	
	public Putstoreasset() {
		super();
	}

	public Putstoreasset(String id){
		super(id);
	}

	@ExcelField(title="资产名称", align=2, sort=7)
	public String getAssetname() {
		return assetname;
	}

	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}
	
	@ExcelField(title="规格/型号", align=2, sort=8)
	public String getSpecimodel() {
		return specimodel;
	}

	public void setSpecimodel(String specimodel) {
		this.specimodel = specimodel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="购买日期不能为空")
	@ExcelField(title="购买日期", align=2, sort=9)
	public Date getBuytime() {
		return buytime;
	}

	public void setBuytime(Date buytime) {
		this.buytime = buytime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="入库日期不能为空")
	@ExcelField(title="入库日期", align=2, sort=10)
	public Date getPutassettime() {
		return putassettime;
	}

	public void setPutassettime(Date putassettime) {
		this.putassettime = putassettime;
	}
	
	@NotNull(message="金额不能为空")
	@ExcelField(title="金额", align=2, sort=11)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=12)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@ExcelField(title="存入地点", align=2, sort=13)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotNull(message="财务折旧年限（年）不能为空")
	@ExcelField(title="财务折旧年限（年）", align=2, sort=14)
	public Integer getYearnum() {
		return yearnum;
	}

	public void setYearnum(Integer yearnum) {
		this.yearnum = yearnum;
	}
	
	@NotNull(message="资产净值不能为空")
	@ExcelField(title="资产净值", align=2, sort=15)
	public Double getAssetvalue() {
		return assetvalue;
	}

	public void setAssetvalue(Double assetvalue) {
		this.assetvalue = assetvalue;
	}
	
}
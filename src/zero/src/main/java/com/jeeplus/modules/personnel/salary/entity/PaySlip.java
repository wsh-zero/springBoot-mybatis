/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.entity;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.personnel.plan.entity.Station;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工资条Entity
 * @author ww
 * @version 2019-03-29
 */
public class PaySlip extends DataEntity<PaySlip> {
	
	private static final long serialVersionUID = 1L;
	private Staff code;		// 员工编号
	private Staff name;		// 姓名
	private Office depart;		// 部门
	private Station station;		// 岗位
	private Date releaseTime;		// 发放月
	private String status;		// 工资状态
	private Double preWage;		// 税前工资
	private Double socialSecurity;		// 社保
	private Double accumulation;		// 公积金
	private Double titleBonus;		// 职称奖金
	private Double gradeBonus;		// 等级奖金
	private Double qualityBonus;		// 资质奖金
	private Double educationBonus;		// 学历奖金
	private Double displayBonus;		// 基础绩效奖金
	private Double punish;		// 行政处罚
	private Double tax;		// 个税
	private Double attendance;		// 考勤
	private Double wage;		// 实得月工资
	private Integer	achPoint;		// 绩效得分
	private Double achBonus;		// 实得绩效奖金
	private Double total;		// 合计

	private boolean isSelf;

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean self) {
		isSelf = self;
	}

	public PaySlip() {
		super();
	}

	public PaySlip(String id){
		super(id);
	}

	@ExcelField(title="员工编号", fieldType=Staff.class, value="code.code", align=2, sort=5)
	public Staff getCode() {
		return code;
	}

	public void setCode(Staff code) {
		this.code = code;
	}
	
	@ExcelField(title="姓名", fieldType=Staff.class, value="name.name", align=2, sort=6)
	public Staff getName() {
		return name;
	}

	public void setName(Staff name) {
		this.name = name;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="depart.name", align=2, sort=7)
	public Office getDepart() {
		return depart;
	}

	public void setDepart(Office depart) {
		this.depart = depart;
	}
	
	@ExcelField(title="岗位", fieldType=Station.class, value="station.name", align=2, sort=8)
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
	@JsonFormat(pattern = "yyyy-MM")
	@ExcelField(title="发放月", align=2, sort=9)
	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	@ExcelField(title="工资状态", dictType="salary_status", align=2, sort=10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="税前工资", align=2, sort=11)
	public Double getPreWage() {
		return preWage;
	}

	public void setPreWage(Double preWage) {
		this.preWage = preWage;
	}
	
	@ExcelField(title="社保", align=2, sort=12)
	public Double getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(Double socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	
	@ExcelField(title="公积金", align=2, sort=13)
	public Double getAccumulation() {
		return accumulation;
	}

	public void setAccumulation(Double accumulation) {
		this.accumulation = accumulation;
	}
	
	@ExcelField(title="职称奖金", align=2, sort=14)
	public Double getTitleBonus() {
		return titleBonus;
	}

	public void setTitleBonus(Double titleBonus) {
		this.titleBonus = titleBonus;
	}
	
	@ExcelField(title="等级奖金", align=2, sort=15)
	public Double getGradeBonus() {
		return gradeBonus;
	}

	public void setGradeBonus(Double gradeBonus) {
		this.gradeBonus = gradeBonus;
	}
	
	@ExcelField(title="资质奖金", align=2, sort=16)
	public Double getQualityBonus() {
		return qualityBonus;
	}

	public void setQualityBonus(Double qualityBonus) {
		this.qualityBonus = qualityBonus;
	}
	
	@ExcelField(title="学历奖金", align=2, sort=17)
	public Double getEducationBonus() {
		return educationBonus;
	}

	public void setEducationBonus(Double educationBonus) {
		this.educationBonus = educationBonus;
	}
	
	@ExcelField(title="基础绩效奖金", align=2, sort=18)
	public Double getDisplayBonus() {
		return displayBonus;
	}

	public void setDisplayBonus(Double displayBonus) {
		this.displayBonus = displayBonus;
	}
	
	@ExcelField(title="行政处罚", align=2, sort=19)
	public Double getPunish() {
		return punish;
	}

	public void setPunish(Double punish) {
		this.punish = punish;
	}
	
	@ExcelField(title="个税", align=2, sort=20)
	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}
	
	@ExcelField(title="考勤", align=2, sort=21)
	public Double getAttendance() {
		return attendance;
	}

	public void setAttendance(Double attendance) {
		this.attendance = attendance;
	}
	
	@ExcelField(title="实得月工资", align=2, sort=22)
	public Double getWage() {
		return wage;
	}

	public void setWage(Double wage) {
		this.wage = wage;
	}
	
	@ExcelField(title="绩效得分", align=2, sort=23)
	public Integer getAchPoint() {
		return achPoint;
	}

	public void setAchPoint(Integer achPoint) {
		this.achPoint = achPoint;
	}
	
	@ExcelField(title="实得绩效奖金", align=2, sort=24)
	public Double getAchBonus() {
		return achBonus;
	}

	public void setAchBonus(Double achBonus) {
		this.achBonus = achBonus;
	}
	
	@ExcelField(title="合计", align=2, sort=25)
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
}
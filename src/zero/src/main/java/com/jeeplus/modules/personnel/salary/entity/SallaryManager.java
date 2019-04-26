/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.entity;

import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.personnel.plan.entity.TitleBonus;
import com.jeeplus.modules.personnel.plan.entity.GradeBonus;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工薪酬管理Entity
 * @author 王伟
 * @version 2019-03-18
 */
public class SallaryManager extends DataEntity<SallaryManager> {
	
	private static final long serialVersionUID = 1L;
	private Staff code;		// 员工编号
	private Staff name;		// 姓名
	private Office depart;		// 部门
	private Station station;		// 岗位
	private StaffStatus status;		// 员工状态
	private Double preWage;		// 税前工资
	private Double socialSecurity;		// 社保
	private Double accumulation;		// 公积金
	private TitleBonus title;		// 职称
	private TitleBonus titleBonus;		// 职称奖金
	private GradeBonus grade;		// 等级
	private GradeBonus gradeBonus;		// 等级奖金
	private Double qualityBonus;		// 资质奖金
	private Double displayBonus;		// 基础绩效奖金
	private Double educationBonus;      //学历奖金
	
	public SallaryManager() {
		super();
	}

	public SallaryManager(String id){
		super(id);
	}


	@ExcelField(title="员工编号", fieldType=Staff.class, value="code.code", align=2, sort=4)
	public Staff getCode() {
		return code;
	}

	public void setCode(Staff code) {
		this.code = code;
	}
	

	@ExcelField(title="姓名", fieldType=Staff.class, value="name.name", align=2, sort=5)
	public Staff getName() {
		return name;
	}

	public void setName(Staff name) {
		this.name = name;
	}
	

	@ExcelField(title="部门", fieldType=Office.class, value="depart.name", align=2, sort=6)
	public Office getDepart() {
		return depart;
	}

	public void setDepart(Office depart) {
		this.depart = depart;
	}
	

	@ExcelField(title="岗位", fieldType=Station.class, value="station.name", align=2, sort=7)
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	

	@ExcelField(title="员工状态", fieldType=Staff.class, value="status.status", align=2, sort=8)
	public StaffStatus getStatus() {
		return status;
	}

	public void setStatus(StaffStatus status) {
		this.status = status;
	}
	
	@NotNull(message="税前工资不能为空")
	@ExcelField(title="税前工资", align=2, sort=9)
	public Double getPreWage() {
		return preWage;
	}

	public void setPreWage(Double preWage) {
		this.preWage = preWage;
	}
	
	@NotNull(message="社保不能为空")
	@ExcelField(title="社保", align=2, sort=10)
	public Double getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(Double socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	
	@NotNull(message="公积金不能为空")
	@ExcelField(title="公积金", align=2, sort=11)
	public Double getAccumulation() {
		return accumulation;
	}

	public void setAccumulation(Double accumulation) {
		this.accumulation = accumulation;
	}
	
	@NotNull(message="职称不能为空")
	@ExcelField(title="职称", fieldType=TitleBonus.class, value="title.name", align=2, sort=12)
	public TitleBonus getTitle() {
		return title;
	}

	public void setTitle(TitleBonus title) {
		this.title = title;
	}
	

	@ExcelField(title="职称奖金", fieldType=TitleBonus.class, value="titleBonus.bonus", align=2, sort=13)
	public TitleBonus getTitleBonus() {
		return titleBonus;
	}

	public void setTitleBonus(TitleBonus titleBonus) {
		this.titleBonus = titleBonus;
	}
	
	@NotNull(message="等级不能为空")
	@ExcelField(title="等级", fieldType=GradeBonus.class, value="grade.grade", align=2, sort=14)
	public GradeBonus getGrade() {
		return grade;
	}

	public void setGrade(GradeBonus grade) {
		this.grade = grade;
	}
	

	@ExcelField(title="等级奖金", fieldType=GradeBonus.class, value="gradeBonus.bonus", align=2, sort=15)
	public GradeBonus getGradeBonus() {
		return gradeBonus;
	}

	public void setGradeBonus(GradeBonus gradeBonus) {
		this.gradeBonus = gradeBonus;
	}
	
	@NotNull(message="资质奖金不能为空")
	@ExcelField(title="资质奖金", align=2, sort=16)
	public Double getQualityBonus() {
		return qualityBonus;
	}

	public void setQualityBonus(Double qualityBonus) {
		this.qualityBonus = qualityBonus;
	}
	
	@NotNull(message="基础绩效奖金不能为空")
	@ExcelField(title="基础绩效奖金", align=2, sort=17)
	public Double getDisplayBonus() {
		return displayBonus;
	}

	public void setDisplayBonus(Double displayBonus) {
		this.displayBonus = displayBonus;
	}

	@NotNull(message="学历奖金不能为空")
	@ExcelField(title="学历奖金", align=2, sort=18)
	public Double getEducationBonus() {
		return educationBonus;
	}

	public void setEducationBonus(Double educationBonus) {
		this.educationBonus = educationBonus;
	}
}
package com.jeeplus.modules.personnel.vo;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.plan.entity.GradeBonus;
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.personnel.plan.entity.TitleBonus;
import com.jeeplus.modules.sys.entity.Office;

import javax.validation.constraints.NotNull;

public class SallaryManagerVo extends DataEntity<SallaryManagerVo> {

    private static final long serialVersionUID = 1L;
    private String code;		// 员工编号
    private String name;		// 姓名
    private String depart;		// 部门
    private String station;		// 岗位
    private String status;		// 员工状态
    private String preWage;		// 税前工资
    private String socialSecurity;		// 社保
    private String accumulation;		// 公积金
    private String title;		// 职称
    private String titleBonus;		// 职称奖金
    private String grade;		// 等级
    private String gradeBonus;		// 等级奖金
    private String qualityBonus;		// 资质奖金
    private String displayBonus;		// 基础绩效奖金
    private String educationBonus;      //学历奖金

    public SallaryManagerVo() {
        super();
    }

    public SallaryManagerVo(String id){
        super(id);
    }


    @ExcelField(title="员工编号", fieldType=Staff.class, value="code.code", align=2, sort=4)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @ExcelField(title="姓名", fieldType=Staff.class, value="name.name", align=2, sort=5)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @ExcelField(title="部门", fieldType=Office.class, value="depart.name", align=2, sort=6)
    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }


    @ExcelField(title="岗位", fieldType=Station.class, value="station.name", align=2, sort=7)
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }


    @ExcelField(title="员工状态", fieldType=Staff.class, value="status.status", align=2, sort=8)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NotNull(message="税前工资不能为空")
    @ExcelField(title="税前工资", align=2, sort=9)
    public String getPreWage() {
        return preWage;
    }

    public void setPreWage(String preWage) {
        this.preWage = preWage;
    }

    @NotNull(message="社保不能为空")
    @ExcelField(title="社保", align=2, sort=10)
    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    @NotNull(message="公积金不能为空")
    @ExcelField(title="公积金", align=2, sort=11)
    public String getAccumulation() {
        return accumulation;
    }

    public void setAccumulation(String accumulation) {
        this.accumulation = accumulation;
    }

    @NotNull(message="职称不能为空")
    @ExcelField(title="职称", fieldType=TitleBonus.class, value="title.name", align=2, sort=12)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @ExcelField(title="职称奖金", fieldType=TitleBonus.class, value="titleBonus.bonus", align=2, sort=13)
    public String getTitleBonus() {
        return titleBonus;
    }

    public void setTitleBonus(String titleBonus) {
        this.titleBonus = titleBonus;
    }

    @NotNull(message="等级不能为空")
    @ExcelField(title="等级", fieldType=GradeBonus.class, value="grade.grade", align=2, sort=14)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


    @ExcelField(title="等级奖金", fieldType=GradeBonus.class, value="gradeBonus.bonus", align=2, sort=15)
    public String getGradeBonus() {
        return gradeBonus;
    }

    public void setGradeBonus(String gradeBonus) {
        this.gradeBonus = gradeBonus;
    }

    @NotNull(message="资质奖金不能为空")
    @ExcelField(title="资质奖金", align=2, sort=16)
    public String getQualityBonus() {
        return qualityBonus;
    }

    public void setQualityBonus(String qualityBonus) {
        this.qualityBonus = qualityBonus;
    }

    @NotNull(message="基础绩效奖金不能为空")
    @ExcelField(title="基础绩效奖金", align=2, sort=17)
    public String getDisplayBonus() {
        return displayBonus;
    }

    public void setDisplayBonus(String displayBonus) {
        this.displayBonus = displayBonus;
    }

    @NotNull(message="学历奖金不能为空")
    @ExcelField(title="学历奖金", align=2, sort=18)
    public String getEducationBonus() {
        return educationBonus;
    }

    public void setEducationBonus(String educationBonus) {
        this.educationBonus = educationBonus;
    }
}


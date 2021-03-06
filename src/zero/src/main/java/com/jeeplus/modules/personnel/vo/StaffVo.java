/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.vo;

import com.jeeplus.modules.personnel.plan.entity.Org;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.personnel.plan.entity.Education;
import com.jeeplus.modules.personnel.plan.entity.Rank;
import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.manage.entity.StaffType;
import com.jeeplus.modules.personnel.attendance.entity.AttendanceType;
import com.jeeplus.modules.personnel.manage.entity.ContractType;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 员工基本信息Entity
 * @author 王伟
 * @version 2019-02-22
 */
public class StaffVo extends DataEntity<StaffVo> {

    private static final long serialVersionUID = 1L;
    private String name;		// 员工姓名
    private String code;		// 员工编号
    private String company;		// 公司
    private String depart;		// 所属部门
    private String sex;		// 性别
    private String station;		// 岗位
    private String leader;		// 上级领导
    private Date birthDate;		// 出生日期
    private String idCard;		// 身份证号
    private String marriage;		// 婚姻状况
    private String education;		// 学历
    private String rank;		// 员工职级
    private String status;		// 员工状态
    private String staffType;		// 员工类型
    private String contactType;		// 联系方式
    private Date entryDate;		// 入职时间
    private String number;		// 自增数字
    private String nation;		// 民族
    private String graduation;		// 毕业学校
    private String major;		// 专业
    private String nativePlace;		// 户口
    private String register;		// 籍贯
    private String detailPlace;		// 详细地址
    private Date graduationTime;		// 毕业时间
    private String socialSecurity;		// 社保号码
    private String political;		// 政治面貌
    private String workYear;		// 本单位工龄


    public StaffVo() {
        super();
    }

    public StaffVo(String id){
        super(id);
    }

    @ExcelField(title="员工姓名(必填)", align=2, sort=1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title="员工编号(不填)", align=2, sort=2)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull(message="公司不能为空")
    @ExcelField(title="公司(必填)", align=2, sort=3)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @NotNull(message="所属部门不能为空")
    @ExcelField(title="所属部门(必填)",  align=2, sort=4)
    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    @ExcelField(title="性别(必填)", dictType="sex", align=2, sort=5)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @NotNull(message="岗位不能为空")
    @ExcelField(title="岗位(必填)",align=2, sort=6)
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @ExcelField(title="上级领导（填写领导员工姓名）", align=2, sort=7)
    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="出生日期不能为空")
    @ExcelField(title="出生日期(必填)", align=2, sort=8)
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @ExcelField(title="身份证号(必填)", align=2, sort=9)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @ExcelField(title="婚姻状况(必填)",dictType="marriage", sort=10)
    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    @NotNull(message="学历不能为空")
    @ExcelField(title="学历(必填)",  align=2, sort=11)
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @NotNull(message="员工职级不能为空")
    @ExcelField(title="员工职级(必填)",  align=2, sort=12)
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @NotNull(message="员工状态不能为空")
    @ExcelField(title="员工状态(必填)",  align=2, sort=13)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NotNull(message="员工类型不能为空")
    @ExcelField(title="员工类型(必填)）", align=2, sort=14)
    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    @ExcelField(title="联系方式(必填)", align=2, sort=15)
    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelField(title="入职时间(必填)", align=2, sort=16)
    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    @ExcelField(title="民族", dictType="nation", align=2, sort=17)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @ExcelField(title="毕业学校", align=2, sort=18)
    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    @ExcelField(title="专业", align=2, sort=19)
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @NotNull(message="户口不能为空")
    @ExcelField(title="户口",  align=2, sort=20)
    public String getnativePlace() {
        return nativePlace;
    }

    public void setnativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    @NotNull(message="籍贯不能为空")
    @ExcelField(title="籍贯",  align=2, sort=21)
    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    @ExcelField(title="详细地址", align=2, sort=22)
    public String getDetailPlace() {
        return detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="毕业时间不能为空")
    @ExcelField(title="毕业时间", align=2, sort=23)
    public Date getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(Date graduationTime) {
        this.graduationTime = graduationTime;
    }

    @ExcelField(title="社保号码", align=2, sort=24)
    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    @ExcelField(title="政治面貌", dictType="political", align=2, sort=25)
    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

}
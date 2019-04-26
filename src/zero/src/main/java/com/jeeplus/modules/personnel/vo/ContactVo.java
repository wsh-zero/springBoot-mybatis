/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.vo;


import com.jeeplus.modules.personnel.manager.entity.Staff;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.personnel.plan.entity.Org;
import com.jeeplus.modules.personnel.plan.entity.Station;
import org.hibernate.validator.constraints.Email;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 导入用实体类
 * 员工通讯录Vo
 * @author 王伟
 * @version 2019-01-30
 */
public class ContactVo extends DataEntity<ContactVo> {

    private static final long serialVersionUID = 1L;
    private String staffId;		// 员工id
    private Staff name;		// 姓名
    private Org company;		// 公司
    private Org depart;		// 所属部门
    private String staffCode;		// 员工编号
    private Station station;		// 岗位
    private Staff contactType;		// 联系方式
    private String email;		// 个人邮箱
    private String officeEmail;		// 办公邮箱
    private String qq;		// QQ号码
    private String weChat;		// 微信
    private String status;		// 状态

    public ContactVo() {
        super();
    }

    public ContactVo(String id){
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
    public Org getCompany() {
        return company;
    }

    public void setCompany(Org company) {
        this.company = company;
    }

    @ExcelField(title="所属部门", fieldType=Org.class, value="depart.name", align=2, sort=3)
    public Org getDepart() {
        return depart;
    }

    public void setDepart(Org depart) {
        this.depart = depart;
    }

    @ExcelField(title="员工编号",  align=2, sort=4)
    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
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
    @ExcelField(title="个人邮箱", align=2, sort=7)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Email(message="办公邮箱必须为合法邮箱")
    @ExcelField(title="办公邮箱", align=2, sort=8)
    public String getOfficeEmail() {
        return officeEmail;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    @ExcelField(title="QQ号码", align=2, sort=9)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @ExcelField(title="微信", align=2, sort=10)
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
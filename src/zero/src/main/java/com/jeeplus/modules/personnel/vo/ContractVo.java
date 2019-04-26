package com.jeeplus.modules.personnel.vo;

/**
 * Created by DRYJKUIL on 2019/2/13.
 */
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.personnel.manage.entity.ContractType;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.plan.entity.Org;
import com.jeeplus.modules.personnel.plan.entity.Station;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 合同管理Entity
 * @author 王伟
 * @version 2019-02-11
 */
public class ContractVo extends DataEntity<ContractVo> {

    private static final long serialVersionUID = 1L;
    private String staff;		// 员工id
    private String staffCode;		// 员工编号
    private String contractCode;		// 合同编号
    private ContractType contractType;		// 合同类型
    private Staff staffName;		// 姓名
    private String sex;		// 性别
    private Date contractStart;		// 合同开始日期
    private Date contractEnd;		// 合同终止日期
    private Org depart;		// 所属部门
    private Station station;		// 岗位
    private Staff idCard;		// 身份证号
    private Staff contactType;		// 联系方式
    private String secretCode;		// 保密协议编号
    private Date secretStart;		// 协议起始日
    private Date secretEnd;		// 协议终止日
    private String signNumber;		// 签订次数

    public ContractVo() {
        super();
    }

    public ContractVo(String id){
        super(id);
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    @ExcelField(title="员工编号(必填)", fieldType=Staff.class, value="staffCode.code", align=2, sort=1)
    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    @ExcelField(title="合同编号(必填)", align=2, sort=2)
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    @ExcelField(title="合同类型", fieldType=ContractType.class, value="contractType.name", align=2, sort=3)
    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    @ExcelField(title="姓名", fieldType=Staff.class, value="staffName.name", align=2, sort=4)
    public Staff getStaffName() {
        return staffName;
    }

    public void setStaffName(Staff staffName) {
        this.staffName = staffName;
    }

    @ExcelField(title="性别(男,女)", dictType="sex", align=2, sort=5)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message="合同开始日期不能为空")
    @ExcelField(title="合同开始日期（必填）", align=2, sort=6)
    public Date getContractStart() {
        return contractStart;
    }

    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message="合同终止日期不能为空")
    @ExcelField(title="合同终止日期（必填）", align=2, sort=7)
    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    @ExcelField(title="所属部门", fieldType=Org.class, value="depart.name", align=2, sort=8)
    public Org getDepart() {
        return depart;
    }

    public void setDepart(Org depart) {
        this.depart = depart;
    }

    @ExcelField(title="岗位", fieldType=Station.class, value="station.name", align=2, sort=9)
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    @ExcelField(title="身份证号", fieldType=Staff.class, value="idCard.idCard", align=2, sort=10)
    public Staff getIdCard() {
        return idCard;
    }

    public void setIdCard(Staff idCard) {
        this.idCard = idCard;
    }

    @ExcelField(title="联系方式", fieldType=Staff.class, value="contactType.contactType", align=2, sort=11)
    public Staff getContactType() {
        return contactType;
    }

    public void setContactType(Staff contactType) {
        this.contactType = contactType;
    }

    @ExcelField(title="保密协议编号", align=2, sort=12)
    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message="协议起始日不能为空")
    @ExcelField(title="协议起始日", align=2, sort=13)
    public Date getSecretStart() {
        return secretStart;
    }

    public void setSecretStart(Date secretStart) {
        this.secretStart = secretStart;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message="协议终止日不能为空")
    @ExcelField(title="协议终止日", align=2, sort=14)
    public Date getSecretEnd() {
        return secretEnd;
    }

    public void setSecretEnd(Date secretEnd) {
        this.secretEnd = secretEnd;
    }

    @ExcelField(title="签订次数", align=2, sort=15)
    public String getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(String signNumber) {
        this.signNumber = signNumber;
    }

}

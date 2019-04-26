package com.jeeplus.modules.personnel.vo;

/**
 * Created by DRYJKUIL on 2019/2/13.
 */
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

import com.jeeplus.common.utils.excel.annotation.ExcelField;



        import com.jeeplus.modules.personnel.manager.entity.Staff;
        import javax.validation.constraints.NotNull;
        import com.jeeplus.modules.personnel.plan.entity.Org;
        import com.jeeplus.modules.personnel.plan.entity.Station;
        import com.jeeplus.modules.personnel.manage.entity.StaffStatus;

        import com.jeeplus.core.persistence.DataEntity;
        import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工资卡Entity
 * @author 王伟
 * @version 2019-01-31
 */
public class SalaryCardVo extends DataEntity<SalaryCardVo> {

    private static final long serialVersionUID = 1L;
    private Staff name;		// 名字
    private String sex;		// 性别
    private Org depart;		// 所属部门
    private Station station;		// 岗位
    private String idCard;		// 身份证号
    private Staff contactType;		// 联系方式
    private String bank;		// 开户行
    private String bankCard;		// 工资卡号
    private String salaryStatus;		// 工资卡状态
    private StaffStatus staffStatus;		// 员工状态
    private String staffId;		// 员工id

    public SalaryCardVo() {
        super();
    }

    public SalaryCardVo(String id){
        super(id);
    }

    @NotNull(message="名字不能为空")
    @ExcelField(title="名字", fieldType=Staff.class, value="name.name", align=2, sort=1)
    public Staff getName() {
        return name;
    }

    public void setName(Staff name) {
        this.name = name;
    }

    @ExcelField(title="性别", dictType="sex", align=2, sort=2)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @ExcelField(title="所属部门", fieldType=Org.class, value="depart.name", align=2, sort=3)
    public Org getDepart() {
        return depart;
    }

    public void setDepart(Org depart) {
        this.depart = depart;
    }

    @ExcelField(title="岗位", fieldType=Station.class, value="station.name", align=2, sort=4)
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    @ExcelField(title="身份证号", fieldType=Staff.class, value="idCard.idCard", align=2, sort=5)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @ExcelField(title="联系方式", fieldType=Staff.class, value="contactType.contactType", align=2, sort=6)
    public Staff getContactType() {
        return contactType;
    }

    public void setContactType(Staff contactType) {
        this.contactType = contactType;
    }

    @ExcelField(title="开户行", align=2, sort=7)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @ExcelField(title="工资卡号", align=2, sort=8)
    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    @ExcelField(title="工资卡状态", dictType="usage_state", align=2, sort=9)
    public String getSalaryStatus() {
        return salaryStatus;
    }

    public void setSalaryStatus(String salaryStatus) {
        this.salaryStatus = salaryStatus;
    }

    @ExcelField(title="员工状态", fieldType=StaffStatus.class, value="staffStatus.status", align=2, sort=10)
    public StaffStatus getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(StaffStatus staffStatus) {
        this.staffStatus = staffStatus;
    }


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

}

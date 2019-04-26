package com.jeeplus.modules.personnel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.common.utils.excel.fieldtype.RoleListType;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Role;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

public class UserApp extends DataEntity<UserApp> {
    private static final long serialVersionUID = 1L;
    private String company;    // 归属公司
    private String office;    // 归属部门
    private String loginName;// 登录名
    private String name;    // 姓名
    private String email;    // 邮箱
    private String mobile;    // 手机
    private String photo;    // 头像
    private String qq;      //QQ
    private String weChat;   //微信
    private String station;  //岗位

    public UserApp() {

    }

    private Role role;    // 根据角色查询用户条件

    private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getQq() {
        return qq;
    }

    public UserApp(String id) {
        super(id);
    }

    public UserApp(String id, String loginName) {
        super(id);
        this.loginName = loginName;
    }

    public UserApp(Role role) {
        super();
        this.role = role;
    }

    public String getPhoto() {
        if ("".equals(photo)) {
            return SpringContextHolder.getStatic() + "/common/images/flat-avatar.png";
        }
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getId() {
        return id;
    }


    @Length(min = 1, max = 100, message = "登录名长度必须介于 1 和 100 之间")
    @ExcelField(title = "登录名", align = 2, sort = 30)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    @Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
    @ExcelField(title = "姓名", align = 2, sort = 40)
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Email(message = "邮箱格式不正确")
    @Length(min = 0, max = 200, message = "邮箱长度必须介于 1 和 200 之间")
    @ExcelField(title = "邮箱", align = 1, sort = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Length(min = 0, max = 200, message = "手机长度必须介于 1 和 200 之间")
    @ExcelField(title = "手机", align = 2, sort = 70)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @ExcelField(title = "备注", align = 1, sort = 900)
    public String getRemarks() {
        return remarks;
    }

    @ExcelField(title = "创建时间", type = 0, align = 1, sort = 90)
    public Date getCreateDate() {
        return createDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonIgnore
    @ExcelField(title = "拥有角色", align = 1, sort = 800, fieldType = RoleListType.class)
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @JsonIgnore
    public List<String> getRoleIdList() {
        List<String> roleIdList = Lists.newArrayList();
        for (Role role : roleList) {
            roleIdList.add(role.getId());
        }
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        roleList = Lists.newArrayList();
        for (String roleId : roleIdList) {
            Role role = new Role();
            role.setId(roleId);
            roleList.add(role);
        }
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

}

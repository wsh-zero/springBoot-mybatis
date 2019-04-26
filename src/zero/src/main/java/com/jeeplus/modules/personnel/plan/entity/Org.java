/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.personnel.plan.entity.OrgType;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 组织Entity
 * @author 王伟
 * @version 2019-02-14
 */
public class Org extends TreeEntity<Org> {
	
	private static final long serialVersionUID = 1L;
	private String departCode;		// 部门编号
	private OrgType orgType;		// 组织类型
	private Staff master;		// 负责人
	private Date establish;		// 成立时间
	private String address;		// 所在地
	private String detailAddress;		// 详细地址
	
	
	public Org() {
		super();
	}

	public Org(String id){
		super(id);
	}

	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}
	
	@NotNull(message="组织类型不能为空")
	public OrgType getOrgType() {
		return orgType;
	}

	public void setOrgType(OrgType orgType) {
		this.orgType = orgType;
	}
	
	public Staff getMaster() {
		return master;
	}

	public void setMaster(Staff master) {
		this.master = master;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEstablish() {
		return establish;
	}

	public void setEstablish(Date establish) {
		this.establish = establish;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	public  Org getParent() {
			return parent;
	}
	
	@Override
	public void setParent(Org parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}
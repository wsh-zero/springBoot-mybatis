/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.achieve.entity;

import com.jeeplus.modules.personnel.plan.entity.Rank;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 对象配置Entity
 * @author ww
 * @version 2019-04-08
 */
public class ObjConf extends DataEntity<ObjConf> {
	
	private static final long serialVersionUID = 1L;
	private Rank rank;		// 职级名称
	private String group;		// 分组名称
	private String departs;		// 部门名称
	private String departName;
	
	public ObjConf() {
		super();
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public ObjConf(String id){
		super(id);
	}

	@NotNull(message="职级名称不能为空")
	@ExcelField(title="职级名称", fieldType=Rank.class, value="rank.name", align=2, sort=6)
	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	@ExcelField(title="分组名称", align=2, sort=7)
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	@ExcelField(title="部门名称", dictType="", align=2, sort=8)
	public String getDeparts() {
		return departs;
	}

	public void setDeparts(String departs) {
		this.departs = departs;
	}
	
}
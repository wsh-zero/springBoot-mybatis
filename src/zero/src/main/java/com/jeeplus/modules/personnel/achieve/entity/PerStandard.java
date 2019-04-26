/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.achieve.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 评定标准Entity
 * @author ww
 * @version 2019-04-08
 */
public class PerStandard extends DataEntity<PerStandard> {
	
	private static final long serialVersionUID = 1L;
	private String perConfId;		// 评定配置表id
	private String name;		// 考核标准
	private String proportion;		// 比例
	
	public PerStandard() {
		super();
	}

	public PerStandard(String id){
		super(id);
	}

	@ExcelField(title="评定配置表id", align=2, sort=6)
	public String getPerConfId() {
		return perConfId;
	}

	public void setPerConfId(String perConfId) {
		this.perConfId = perConfId;
	}
	
	@ExcelField(title="考核标准", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="比例", align=2, sort=8)
	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	
}
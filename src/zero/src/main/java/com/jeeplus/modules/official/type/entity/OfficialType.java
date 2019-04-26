/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.type.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 公文类型管理Entity
 * @author xy
 * @version 2019-02-20
 */
public class OfficialType extends DataEntity<OfficialType> {
	
	private static final long serialVersionUID = 1L;
	private String typename;		// 类型名称
	
	public OfficialType() {
		super();
	}

	public OfficialType(String id){
		super(id);
	}

	@ExcelField(title="类型名称", align=2, sort=7)
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
}
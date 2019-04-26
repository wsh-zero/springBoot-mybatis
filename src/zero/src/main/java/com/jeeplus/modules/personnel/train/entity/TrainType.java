/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.train.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 培训类型Entity
 * @author 王伟
 * @version 2019-02-19
 */
public class TrainType extends DataEntity<TrainType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 培训类型
	
	public TrainType() {
		super();
	}

	public TrainType(String id){
		super(id);
	}

	@ExcelField(title="培训类型", align=2, sort=5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
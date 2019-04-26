/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.t_teacher.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 教师Entity
 * @author xy
 * @version 2019-01-25
 */
public class T_Teacher extends DataEntity<T_Teacher> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 教师姓名
	
	public T_Teacher() {
		super();
	}

	public T_Teacher(String id){
		super(id);
	}

	@ExcelField(title="教师姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
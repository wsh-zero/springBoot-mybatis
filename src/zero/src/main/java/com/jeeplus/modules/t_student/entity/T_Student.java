/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.t_student.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 学生Entity
 * @author xy
 * @version 2019-01-25
 */
public class T_Student extends DataEntity<T_Student> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 学生姓名
	
	public T_Student() {
		super();
	}

	public T_Student(String id){
		super(id);
	}

	@ExcelField(title="学生姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
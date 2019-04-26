/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.studentteacher.entity;

import com.jeeplus.modules.t_student.entity.T_Student;
import com.jeeplus.modules.t_teacher.entity.T_Teacher;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 学生老师Entity
 * @author xy
 * @version 2019-01-25
 */
public class StudentTeacher extends DataEntity<StudentTeacher> {
	
	private static final long serialVersionUID = 1L;
	private T_Student student;		// 学生
	private T_Teacher teahcer;		// 教师
	
	public StudentTeacher() {
		super();
	}

	public StudentTeacher(String id){
		super(id);
	}

	@ExcelField(title="学生", fieldType=T_Student.class, value="student.name", align=2, sort=1)
	public T_Student getStudent() {
		return student;
	}

	public void setStudent(T_Student student) {
		this.student = student;
	}
	
	@ExcelField(title="教师", fieldType=T_Teacher.class, value="teahcer.name", align=2, sort=2)
	public T_Teacher getTeahcer() {
		return teahcer;
	}

	public void setTeahcer(T_Teacher teahcer) {
		this.teahcer = teahcer;
	}
	
}
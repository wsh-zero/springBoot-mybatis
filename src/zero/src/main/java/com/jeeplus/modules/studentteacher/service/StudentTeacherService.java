/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.studentteacher.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.studentteacher.entity.StudentTeacher;
import com.jeeplus.modules.studentteacher.mapper.StudentTeacherMapper;

/**
 * 学生老师Service
 * @author xy
 * @version 2019-01-25
 */
@Service
@Transactional(readOnly = true)
public class StudentTeacherService extends CrudService<StudentTeacherMapper, StudentTeacher> {

	public StudentTeacher get(String id) {
		return super.get(id);
	}
	
	public List<StudentTeacher> findList(StudentTeacher studentTeacher) {
		return super.findList(studentTeacher);
	}
	
	public Page<StudentTeacher> findPage(Page<StudentTeacher> page, StudentTeacher studentTeacher) {
		return super.findPage(page, studentTeacher);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentTeacher studentTeacher) {
		super.save(studentTeacher);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentTeacher studentTeacher) {
		super.delete(studentTeacher);
	}
	
}
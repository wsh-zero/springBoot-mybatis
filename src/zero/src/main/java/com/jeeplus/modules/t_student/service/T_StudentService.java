/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.t_student.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.t_student.entity.T_Student;
import com.jeeplus.modules.t_student.mapper.T_StudentMapper;

/**
 * 学生Service
 * @author xy
 * @version 2019-01-25
 */
@Service
@Transactional(readOnly = true)
public class T_StudentService extends CrudService<T_StudentMapper, T_Student> {

	public T_Student get(String id) {
		return super.get(id);
	}
	
	public List<T_Student> findList(T_Student t_Student) {
		return super.findList(t_Student);
	}
	
	public Page<T_Student> findPage(Page<T_Student> page, T_Student t_Student) {
		return super.findPage(page, t_Student);
	}
	
	@Transactional(readOnly = false)
	public void save(T_Student t_Student) {
		super.save(t_Student);
	}
	
	@Transactional(readOnly = false)
	public void delete(T_Student t_Student) {
		super.delete(t_Student);
	}
	
}
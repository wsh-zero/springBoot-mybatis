/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.t_teacher.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.t_teacher.entity.T_Teacher;
import com.jeeplus.modules.t_teacher.mapper.T_TeacherMapper;

/**
 * 教师Service
 * @author xy
 * @version 2019-01-25
 */
@Service
@Transactional(readOnly = true)
public class T_TeacherService extends CrudService<T_TeacherMapper, T_Teacher> {

	public T_Teacher get(String id) {
		return super.get(id);
	}
	
	public List<T_Teacher> findList(T_Teacher t_Teacher) {
		return super.findList(t_Teacher);
	}
	
	public Page<T_Teacher> findPage(Page<T_Teacher> page, T_Teacher t_Teacher) {
		return super.findPage(page, t_Teacher);
	}
	
	@Transactional(readOnly = false)
	public void save(T_Teacher t_Teacher) {
		super.save(t_Teacher);
	}
	
	@Transactional(readOnly = false)
	public void delete(T_Teacher t_Teacher) {
		super.delete(t_Teacher);
	}
	
}
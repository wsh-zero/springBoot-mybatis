/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.empee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.empee.entity.T_Empee;
import com.jeeplus.modules.empee.mapper.T_EmpeeMapper;

/**
 * 员工Service
 * @author xy
 * @version 2019-01-25
 */
@Service
@Transactional(readOnly = true)
public class T_EmpeeService extends CrudService<T_EmpeeMapper, T_Empee> {

	public T_Empee get(String id) {
		return super.get(id);
	}
	
	public List<T_Empee> findList(T_Empee t_Empee) {
		return super.findList(t_Empee);
	}
	
	public Page<T_Empee> findPage(Page<T_Empee> page, T_Empee t_Empee) {
		return super.findPage(page, t_Empee);
	}
	
	@Transactional(readOnly = false)
	public void save(T_Empee t_Empee) {
		super.save(t_Empee);
	}
	
	@Transactional(readOnly = false)
	public void delete(T_Empee t_Empee) {
		super.delete(t_Empee);
	}
	
}
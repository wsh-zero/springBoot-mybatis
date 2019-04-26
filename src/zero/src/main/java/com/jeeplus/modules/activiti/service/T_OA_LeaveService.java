/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.activiti.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.activiti.entity.T_OA_Leave;
import com.jeeplus.modules.activiti.mapper.T_OA_LeaveMapper;

/**
 * 请假申请Service
 * @author xy
 * @version 2019-01-28
 */
@Service
@Transactional(readOnly = true)
public class T_OA_LeaveService extends CrudService<T_OA_LeaveMapper, T_OA_Leave> {

	public T_OA_Leave get(String id) {
		return super.get(id);
	}
	
	public List<T_OA_Leave> findList(T_OA_Leave t_OA_Leave) {
		return super.findList(t_OA_Leave);
	}
	
	public Page<T_OA_Leave> findPage(Page<T_OA_Leave> page, T_OA_Leave t_OA_Leave) {
		return super.findPage(page, t_OA_Leave);
	}
	
	@Transactional(readOnly = false)
	public void save(T_OA_Leave t_OA_Leave) {
		super.save(t_OA_Leave);
	}
	
	@Transactional(readOnly = false)
	public void delete(T_OA_Leave t_OA_Leave) {
		super.delete(t_OA_Leave);
	}
	
}
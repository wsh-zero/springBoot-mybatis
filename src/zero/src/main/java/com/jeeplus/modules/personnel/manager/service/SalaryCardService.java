/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.service;

import java.util.List;

import com.jeeplus.modules.personnel.manager.entity.Contact;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.manager.entity.SalaryCard;
import com.jeeplus.modules.personnel.manager.mapper.SalaryCardMapper;

/**
 * 工资卡Service
 * @author 王伟
 * @version 2019-01-31
 */
@Service
@Transactional(readOnly = true)
public class SalaryCardService extends CrudService<SalaryCardMapper, SalaryCard> {
	@Autowired
	private SalaryCardMapper salaryCardMapper;

	public SalaryCard get(String id) {
		return super.get(id);
	}
	
	public List<SalaryCard> findList(SalaryCard salaryCard) {
		return super.findList(salaryCard);
	}
	
	public Page<SalaryCard> findPage(Page<SalaryCard> page, SalaryCard salaryCard) {
		return super.findPage(page, salaryCard);
	}
	
	@Transactional(readOnly = false)
	public void save(SalaryCard salaryCard) {
		super.save(salaryCard);
	}
	
	@Transactional(readOnly = false)
	public void delete(SalaryCard salaryCard) {
		super.deleteByLogic(salaryCard);
	}

	/**
	 *条件查询
	 * @param salaryCard
	 * @return
	 */
	public SalaryCard find (SalaryCard salaryCard){

		return salaryCardMapper.find(salaryCard);
	}

	public SalaryCard getName(String id) {

		return salaryCardMapper.getName(id);
	}

	public int count(SalaryCard salaryCard){
		return salaryCardMapper.count(salaryCard);
	}
	
}
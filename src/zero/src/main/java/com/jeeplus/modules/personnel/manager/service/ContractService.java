/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.manager.entity.Contract;
import com.jeeplus.modules.personnel.manager.mapper.ContractMapper;

/**
 * 合同管理Service
 * @author 王伟
 * @version 2019-02-11
 */
@Service
@Transactional(readOnly = true)
public class ContractService extends CrudService<ContractMapper, Contract> {
	@Autowired
	private ContractMapper contractMapper;

	public Contract get(String id) {
		return super.get(id);
	}
	
	public List<Contract> findList(Contract contract) {
		return super.findList(contract);
	}
	
	public Page<Contract> findPage(Page<Contract> page, Contract contract) {
		return super.findPage(page, contract);
	}
	
	@Transactional(readOnly = false)
	public void save(Contract contract) {
		super.save(contract);
	}
	
	@Transactional(readOnly = false)
	public void delete(Contract contract) {
		super.delete(contract);
	}
	/**
	 *条件查询
	 * @param salaryCard
	 * @return
	 */
	public Contract find (Contract salaryCard){

		return contractMapper.find(salaryCard);
	}

	public Contract getName(String id) {

		return contractMapper.getName(id);
	}

	public int count(Contract contract){
		return contractMapper.count(contract);
	}
	
}
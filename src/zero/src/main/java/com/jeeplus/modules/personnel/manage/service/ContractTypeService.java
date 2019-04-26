/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.service;

import java.util.List;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.manage.entity.ContractType;
import com.jeeplus.modules.personnel.manage.mapper.ContractTypeMapper;

/**
 * 合同类型Service
 * @author 王伟
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class ContractTypeService extends CrudService<ContractTypeMapper, ContractType> {
	@Autowired
	private ContractTypeMapper contractTypeMapper;

	public ContractType get(String id) {
		return super.get(id);
	}
	
	public List<ContractType> findList(ContractType contractType) {
		return super.findList(contractType);
	}
	
	public Page<ContractType> findPage(Page<ContractType> page, ContractType contractType) {
		return super.findPage(page, contractType);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractType contractType) {
		super.save(contractType);
	}

	@Transactional(readOnly = false)
	public AjaxJson preserve(ContractType contractType) {
		AjaxJson j =new AjaxJson();
		try{
			if (StringUtils.isNotBlank(contractType.getName())){
				ContractType con =new ContractType();
				con.setName(contractType.getName());
				int count = contractTypeMapper.count(con);
				if (count>0){
					j.setSuccess(false);
					j.setMsg("合同类型名称重复");
					return j;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		super.save(contractType);
		j.setSuccess(true);
		j.setMsg("保存合同类型成功");
		return j;
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractType contractType) {
		super.deleteByLogic(contractType);
	}
	
}
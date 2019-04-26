/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.service;

import java.util.List;

import com.jeeplus.common.json.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.plan.entity.OrgType;
import com.jeeplus.modules.personnel.plan.mapper.OrgTypeMapper;

/**
 * 组织类型Service
 * @author 王伟
 * @version 2019-02-15
 */
@Service
@Transactional(readOnly = true)
public class OrgTypeService extends CrudService<OrgTypeMapper, OrgType> {
	@Autowired
	private OrgTypeMapper orgTypeMapper;

	public OrgType get(String id) {
		return super.get(id);
	}
	
	public List<OrgType> findList(OrgType orgType) {
		return super.findList(orgType);
	}
	
	public Page<OrgType> findPage(Page<OrgType> page, OrgType orgType) {
		return super.findPage(page, orgType);
	}
	
	@Transactional(readOnly = false)
	public void save(OrgType orgType) {
		super.save(orgType);
	}

	@Transactional(readOnly = false)
	public AjaxJson preserve (OrgType orgType) {
		AjaxJson j =new AjaxJson();
		try {
			if (orgType.getIsNewRecord()) {
				OrgType org = new OrgType();
				org.setName(orgType.getName());
				int count = orgTypeMapper.count(org);
				if (count > 0) {
					j.setSuccess(false);
					j.setMsg("组织类型名称重复");
					return j;
				}
			}else {
				OrgType org = new OrgType();
				org.setName(orgType.getName());
				OrgType o = orgTypeMapper.find(org);
				if (!o.getId().equals(orgType.getId()) && o != null) {
					j.setSuccess(false);
					j.setMsg("组织类型名称重复");
					return j;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		super.save(orgType);
		j.setSuccess(true);
		j.setMsg("保存组织类型成功");
		return j;
	}
	
	@Transactional(readOnly = false)
	public void delete(OrgType orgType) {
		super.deleteByLogic(orgType);
	}
	
}
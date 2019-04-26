/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.service;

import java.util.List;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.personnel.plan.entity.Org;
import com.jeeplus.modules.personnel.plan.mapper.OrgMapper;

/**
 * 组织Service
 * @author 王伟
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class OrgService extends TreeService<OrgMapper, Org> {

	@Autowired
	private OrgMapper orgMapper;

	public Org get(String id) {
		return super.get(id);
	}
	
	public List<Org> findList(Org org) {
		if (StringUtils.isNotBlank(org.getParentIds())){
			org.setParentIds(","+org.getParentIds()+",");
		}
		return super.findList(org);
	}
	
	@Transactional(readOnly = false)
	public void save(Org org) {
		super.save(org);
	}
	@Transactional(readOnly = false)
	public AjaxJson preserve(Org org) {
		AjaxJson j = new AjaxJson();
		try {
			if (org.getIsNewRecord()) {
				if (StringUtils.isNotBlank(org.getName())) {
					Org o = new Org();
					o.setName(org.getName());
					int count = orgMapper.count(o);
					if (count > 0) {
						j.setSuccess(false);
						j.setMsg("部门名称重复");
						return j;
					}
				}
				if (StringUtils.isNotBlank(org.getDepartCode())) {
					Org o = new Org();
					o.setDepartCode(org.getDepartCode());
					int count = orgMapper.count(o);
					if (count > 0) {
						j.setSuccess(false);
						j.setMsg("部门编号重复");
						return j;
					}
				}
			}else {
				if (StringUtils.isNotBlank(org.getName())) {
					Org o = new Org();
					o.setName(org.getName());
					Org org1 = orgMapper.find(o);
					if (org1!=null&&!org1.getId().equals(org.getId())) {
						j.setSuccess(false);
						j.setMsg("部门名称重复");
						return j;
					}
				}
				if (StringUtils.isNotBlank(org.getDepartCode())) {
					Org o = new Org();
					o.setDepartCode(org.getDepartCode());
					Org org1 = orgMapper.find(o);
					if (org1!=null&&!org1.getId().equals(org.getId())) {
						j.setSuccess(false);
						j.setMsg("部门编号重复");
						return j;
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		super.save(org);
		j.setSuccess(true);
		j.put("org", org);
		j.setMsg("保存组织成功");
		return j;

	}
	
	@Transactional(readOnly = false)
	public void delete(Org org) {
		super.deleteByLogic(org);
	}
	
}
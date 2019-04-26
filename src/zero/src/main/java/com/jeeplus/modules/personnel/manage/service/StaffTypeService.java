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
import com.jeeplus.modules.personnel.manage.entity.StaffType;
import com.jeeplus.modules.personnel.manage.mapper.StaffTypeMapper;

/**
 * 员工类型Service
 * @author 王伟
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class StaffTypeService extends CrudService<StaffTypeMapper, StaffType> {

	@Autowired
	private StaffTypeMapper staffTypeMapper;

	public StaffType get(String id) {
		return super.get(id);
	}
	
	public List<StaffType> findList(StaffType staffType) {
		return super.findList(staffType);
	}
	
	public Page<StaffType> findPage(Page<StaffType> page, StaffType staffType) {
		return super.findPage(page, staffType);
	}
	
	@Transactional(readOnly = false)
	public AjaxJson preserve(StaffType staffType) {
		AjaxJson j =new AjaxJson();
		try{
			if (StringUtils.isNotBlank(staffType.getType())){
				StaffType type = new StaffType();
				type.setType(staffType.getType());
				int count = staffTypeMapper.count(type);
				if (count > 0){
					j.setSuccess(false);
					j.setMsg("员工类型名称重复");
					return j;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		super.save(staffType);
		j.setSuccess(true);
		j.setMsg("保存员工类型成功");
		return j;
	}

	@Transactional(readOnly = false)
	public void save(StaffType staffType) {
		super.save(staffType);
	}
	
	@Transactional(readOnly = false)
	public void delete(StaffType staffType) {
		super.deleteByLogic(staffType);
	}
	
}
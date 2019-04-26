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
import com.jeeplus.modules.personnel.manage.entity.LeaveType;
import com.jeeplus.modules.personnel.manage.mapper.LeaveTypeMapper;

/**
 * 离职类型Service
 * @author 王伟
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class LeaveTypeService extends CrudService<LeaveTypeMapper, LeaveType> {
	@Autowired
	private LeaveTypeMapper leaveTypeMapper;

	public LeaveType get(String id) {
		return super.get(id);
	}
	
	public List<LeaveType> findList(LeaveType leaveType) {
		return super.findList(leaveType);
	}
	
	public Page<LeaveType> findPage(Page<LeaveType> page, LeaveType leaveType) {
		return super.findPage(page, leaveType);
	}
	
	@Transactional(readOnly = false)
	public void save(LeaveType leaveType) {
		super.save(leaveType);
	}
	@Transactional(readOnly = false)
	public AjaxJson preserve(LeaveType leaveType) {
		AjaxJson j =new AjaxJson();
		try{
			if (StringUtils.isNotBlank(leaveType.getType())){
				LeaveType leave = new LeaveType();
				leave.setType(leaveType.getType());
				int count = leaveTypeMapper.count(leave);
				if (count > 0){
					j.setSuccess(false);
					j.setMsg("离职类型名称重复");
					return j;
				}
			}
		}catch (Exception e ){
			e.printStackTrace();
		}

		super.save(leaveType);
		j.setSuccess(true);
		j.setMsg("保存离职类型成功");
		return j;
	}
	
	@Transactional(readOnly = false)
	public void delete(LeaveType leaveType) {
		super.deleteByLogic(leaveType);
	}
	
}
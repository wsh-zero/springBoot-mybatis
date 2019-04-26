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
import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.manage.mapper.StaffStatusMapper;

/**
 * 员工状态Service
 * @author 王伟
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class StaffStatusService extends CrudService<StaffStatusMapper, StaffStatus> {
	@Autowired
	private StaffStatusMapper staffStatusMapper;

	public StaffStatus get(String id) {
		return super.get(id);
	}
	
	public List<StaffStatus> findList(StaffStatus staffStatus) {
		return super.findList(staffStatus);
	}
	
	public Page<StaffStatus> findPage(Page<StaffStatus> page, StaffStatus staffStatus) {
		return super.findPage(page, staffStatus);
	}
	
	@Transactional(readOnly = false)
	public void save(StaffStatus staffStatus) {
		super.save(staffStatus);
	}
	@Transactional(readOnly = false)
	public AjaxJson preserve(StaffStatus staffStatus) {
		AjaxJson j =new AjaxJson();
		try{
			if (StringUtils.isNotBlank(staffStatus.getStatus())){
				StaffStatus status = new StaffStatus();
				status.setStatus(staffStatus.getStatus());
				int count = staffStatusMapper.count(status);
				if (count>0){
					j.setSuccess(false);
					j.setMsg("员工状态名称重复");
					return j;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		super.save(staffStatus);
		j.setSuccess(true);
		j.setMsg("保存员工状态成功");
		return j;
	}
	@Transactional(readOnly = false)
	public void delete(StaffStatus staffStatus) {
		super.deleteByLogic(staffStatus);
	}
	
}
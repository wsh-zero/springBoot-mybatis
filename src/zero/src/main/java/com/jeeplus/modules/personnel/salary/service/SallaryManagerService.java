/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.service;

import java.util.List;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.salary.entity.SallaryManager;
import com.jeeplus.modules.personnel.salary.mapper.SallaryManagerMapper;

/**
 * 员工薪酬管理Service
 * @author 王伟
 * @version 2019-03-18
 */
@Service
@Transactional(readOnly = true)
public class SallaryManagerService extends CrudService<SallaryManagerMapper, SallaryManager> {

	@Autowired
	private SallaryManagerMapper sallaryManagerMapper;
	@Autowired
	private StaffMapper staffMapper;

	public SallaryManager get(String id) {
		return super.get(id);
	}
	
	public List<SallaryManager> findList(SallaryManager sallaryManager) {
		return super.findList(sallaryManager);
	}

	@Transactional(readOnly = false)
	public Page<SallaryManager> findPage(Page<SallaryManager> page, SallaryManager sallaryManager) {
		//执行查询列表之前，去员工列表获取数据，新增员工信息到配置表，新增之前查询配置表中是否存在该信息，存在则不增加，不存在则执行新增操作
		List<Staff> staff = staffMapper.getStaff();
		for (Staff s :staff){
			SallaryManager sta = new SallaryManager();
			sta.setName(s);
			int count = sallaryManagerMapper.count(sta);
			if (count==0){
				super.save(sta);
			}
		}
		return super.findPage(page, sallaryManager);
	}


	
	@Transactional(readOnly = false)
	public void save(SallaryManager sallaryManager) {
		super.save(sallaryManager);
	}

	public SallaryManager find(SallaryManager sallaryManager){
		return sallaryManagerMapper.find(sallaryManager);
	}
	@Transactional(readOnly = false)
	public void delete(SallaryManager sallaryManager) {
		super.deleteByLogic(sallaryManager);
	}
	
}
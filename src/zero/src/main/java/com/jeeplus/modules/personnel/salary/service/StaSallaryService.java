/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.service;

import java.util.List;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.salary.entity.StaSallary;
import com.jeeplus.modules.personnel.salary.mapper.StaSallaryMapper;

/**
 * 员工薪资配置Service
 * @author 王伟
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class StaSallaryService extends CrudService<StaSallaryMapper, StaSallary> {

	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private StaSallaryMapper staSallaryMapper;

	public StaSallary get(String id) {
		return super.get(id);
	}

	public List<StaSallary> findList(StaSallary staSallary) {
		return super.findList(staSallary);
	}

	@Transactional(readOnly = false)
	public Page<StaSallary> findPage(Page<StaSallary> page, StaSallary staSallary) {
		//执行查询列表之前，去员工列表获取数据，新增员工信息到配置表，新增之前查询配置表中是否存在该信息，存在则不增加，不存在则执行新增操作
		List<Staff> staff = staffMapper.getStaff();
		for (Staff s :staff){
			StaSallary sta = new StaSallary();
			sta.setName(s);
			int count = staSallaryMapper.count(sta);
			if (count==0){
				super.save(sta);
			}
		}
		return super.findPage(page, staSallary);
	}

	//	@Transactional(readOnly = false)
//	public AjaxJson preserve(StaSallary staSallary) {
//		AjaxJson j = new AjaxJson();
//		super.save(staSallary);
//		return j;
//	}
	@Transactional(readOnly = false)
	public void save(StaSallary staSallary) {
		super.save(staSallary);
	}

	@Transactional(readOnly = false)
	public void delete(StaSallary staSallary) {
		super.deleteByLogic(staSallary);
	}

}
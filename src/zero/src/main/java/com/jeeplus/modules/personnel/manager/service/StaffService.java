/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.service;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.mapper.StaffMapper;

/**
 * 员工基本信息Service
 * @author ww
 * @version 2019-01-30
 */
@Service
@Transactional(readOnly = true)
public class StaffService extends CrudService<StaffMapper, Staff> {
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private UserMapper userMapper;

	public Staff get(String id) {
		return super.get(id);
	}

	public List<Staff> findList(Staff staff) {
		return super.findList(staff);
	}

	public Page<Staff> findPage(Page<Staff> page, Staff staff) {
		return super.findPage(page, staff);
	}

	@Transactional(readOnly = false)
	public void save(Staff staff) {
		super.save(staff);
	}

	@Transactional(readOnly = false)
	public void delete(Staff staff) {
		super.deleteByLogic(staff);
	}

	/**
	 *条件查询
	 * @param staff
	 * @return
	 */
	public Staff find (Staff staff){
		return staffMapper.find(staff);
	}

	public int count(Staff salaryCard){
		return staffMapper.count(salaryCard);
	}

	public Staff findLeader(String code){
		return staffMapper.findLeader(code);
	}

	/**
	 * 根据岗位获取员工id，在获取系统用户
	 * @param station
	 * @return
	 */
	public List<User> getStaffByStation(String station){
		List<Staff> list =  staffMapper.getStaffByStation(station);
		List<User> userList = new ArrayList<>();
		for (Staff staff :list){
			User user =  userMapper.getUserByStaff(staff.getId());
			if (user!=null){
				userList.add(user);
			}
		}
		return userList;
	}

	/**
	 * 根据系统用户id获取staff上级领导
	 */
	public User getUser(String userId){
		String id  = userMapper.getStaff(userId);
		if (id!=null && id!="") {
			Staff staff = staffMapper.get(id);
			User u = userMapper.getUserByStaff(staff.getLeader().getId());
			if (u != null) {
				return u;
			}
		}
		return null;
	}
}
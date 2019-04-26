/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.attendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.attendance.entity.AttendanceType;
import com.jeeplus.modules.personnel.attendance.mapper.AttendanceTypeMapper;

/**
 * 考勤类型Service
 * @author 王伟
 * @version 2019-02-19
 */
@Service
@Transactional(readOnly = true)
public class AttendanceTypeService extends CrudService<AttendanceTypeMapper, AttendanceType> {
	@Autowired
	private AttendanceTypeMapper attendanceTypeMapper;

	public AttendanceType get(String id) {
		return super.get(id);
	}
	
	public List<AttendanceType> findList(AttendanceType attendanceType) {
		return super.findList(attendanceType);
	}
	
	public Page<AttendanceType> findPage(Page<AttendanceType> page, AttendanceType attendanceType) {
		return super.findPage(page, attendanceType);
	}
	
	@Transactional(readOnly = false)
	public void save(AttendanceType attendanceType) {
		super.save(attendanceType);
	}
	
	@Transactional(readOnly = false)
	public void delete(AttendanceType attendanceType) {
		super.delete(attendanceType);
	}
	@Transactional(readOnly = false)
	public AttendanceType find(AttendanceType attendanceType) {
		return attendanceTypeMapper.find(attendanceType);
	}
	
}
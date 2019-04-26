/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.salary.entity.PenaltyRecord;
import com.jeeplus.modules.personnel.salary.mapper.PenaltyRecordMapper;

/**
 * 行政处罚记录Service
 * @author 王伟
 * @version 2019-03-19
 */
@Service
@Transactional(readOnly = true)
public class PenaltyRecordService extends CrudService<PenaltyRecordMapper, PenaltyRecord> {

	public PenaltyRecord get(String id) {
		return super.get(id);
	}
	
	public List<PenaltyRecord> findList(PenaltyRecord penaltyRecord) {
		return super.findList(penaltyRecord);
	}
	
	public Page<PenaltyRecord> findPage(Page<PenaltyRecord> page, PenaltyRecord penaltyRecord) {
		return super.findPage(page, penaltyRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(PenaltyRecord penaltyRecord) {
		super.save(penaltyRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(PenaltyRecord penaltyRecord) {
		super.delete(penaltyRecord);
	}
	
}
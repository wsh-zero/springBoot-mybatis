/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.train.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.train.entity.TrainType;
import com.jeeplus.modules.personnel.train.mapper.TrainTypeMapper;

/**
 * 培训类型Service
 * @author 王伟
 * @version 2019-02-19
 */
@Service
@Transactional(readOnly = true)
public class TrainTypeService extends CrudService<TrainTypeMapper, TrainType> {

	public TrainType get(String id) {
		return super.get(id);
	}
	
	public List<TrainType> findList(TrainType trainType) {
		return super.findList(trainType);
	}
	
	public Page<TrainType> findPage(Page<TrainType> page, TrainType trainType) {
		return super.findPage(page, trainType);
	}
	
	@Transactional(readOnly = false)
	public void save(TrainType trainType) {
		super.save(trainType);
	}
	
	@Transactional(readOnly = false)
	public void delete(TrainType trainType) {
		super.deleteByLogic(trainType);
	}
	
}
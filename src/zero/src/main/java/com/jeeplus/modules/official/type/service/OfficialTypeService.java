/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.type.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.official.type.entity.OfficialType;
import com.jeeplus.modules.official.type.mapper.OfficialTypeMapper;

/**
 * 公文类型管理Service
 * @author xy
 * @version 2019-02-20
 */
@Service
@Transactional(readOnly = true)
public class OfficialTypeService extends CrudService<OfficialTypeMapper, OfficialType> {

	public OfficialType get(String id) {
		return super.get(id);
	}
	
	public List<OfficialType> findList(OfficialType officialType) {
		return super.findList(officialType);
	}
	
	public Page<OfficialType> findPage(Page<OfficialType> page, OfficialType officialType) {
		return super.findPage(page, officialType);
	}
	
	@Transactional(readOnly = false)
	public void save(OfficialType officialType) {
		super.save(officialType);
	}
	
	@Transactional(readOnly = false)
	public void delete(OfficialType officialType) {
		super.delete(officialType);
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.internalrecord.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.official.internalrecord.entity.InternalOfficalRecord;
import com.jeeplus.modules.official.internalrecord.mapper.InternalOfficalRecordMapper;

/**
 * 内部公文接收状态Service
 * @author chentao
 * @version 2019-04-03
 */
@Service
@Transactional(readOnly = true)
public class InternalOfficalRecordService extends CrudService<InternalOfficalRecordMapper, InternalOfficalRecord> {

	public InternalOfficalRecord get(String id) {
		return super.get(id);
	}
	
	public List<InternalOfficalRecord> findList(InternalOfficalRecord internalOfficalRecord) {
		return super.findList(internalOfficalRecord);
	}
	
	public Page<InternalOfficalRecord> findPage(Page<InternalOfficalRecord> page, InternalOfficalRecord internalOfficalRecord) {
		return super.findPage(page, internalOfficalRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(InternalOfficalRecord internalOfficalRecord) {
		super.save(internalOfficalRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(InternalOfficalRecord internalOfficalRecord) {
		super.delete(internalOfficalRecord);
	}

	@Transactional(readOnly = false)
	public void updateReaded(String id, String user, String readed) {
		mapper.updateReaded(id, user, readed, new Date());
	}
}
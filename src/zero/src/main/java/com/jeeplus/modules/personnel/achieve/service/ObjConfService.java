/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.achieve.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.achieve.entity.ObjConf;
import com.jeeplus.modules.personnel.achieve.mapper.ObjConfMapper;

/**
 * 对象配置Service
 * @author ww
 * @version 2019-04-08
 */
@Service
@Transactional(readOnly = true)
public class ObjConfService extends CrudService<ObjConfMapper, ObjConf> {

	@Autowired
	private ObjConfMapper objConfMapper;

	public ObjConf get(String id) {
		return super.get(id);
	}
	
	public List<ObjConf> findList(ObjConf objConf) {
		return super.findList(objConf);
	}
	
	public Page<ObjConf> findPage(Page<ObjConf> page, ObjConf objConf) {
		return super.findPage(page, objConf);
	}
	
	@Transactional(readOnly = false)
	public void save(ObjConf objConf) {
		super.save(objConf);
	}
	
	@Transactional(readOnly = false)
	public void delete(ObjConf objConf) {
		super.delete(objConf);
	}

	public ObjConf find(ObjConf objConf){
		return objConfMapper.find(objConf);
	}

	public int count(ObjConf objConf){
		return objConfMapper.count(objConf);
	}
	
}
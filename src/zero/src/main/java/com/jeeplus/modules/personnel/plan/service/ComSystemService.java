/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.service;

import java.util.List;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.plan.entity.ComSystem;
import com.jeeplus.modules.personnel.plan.mapper.ComSystemMapper;

/**
 * 制度管理Service
 * @author 王伟
 * @version 2019-02-14
 */
@Service
@Transactional(readOnly = true)
public class ComSystemService extends CrudService<ComSystemMapper, ComSystem> {

	@Autowired
	private ComSystemMapper comSystemMapper;

	public ComSystem get(String id) {
		return super.get(id);
	}
	
	public List<ComSystem> findList(ComSystem comSystem) {
		return super.findList(comSystem);
	}
	
	public Page<ComSystem> findPage(Page<ComSystem> page, ComSystem comSystem) {
		return super.findPage(page, comSystem);
	}
	
	@Transactional(readOnly = false)
	public void save(ComSystem comSystem) {
		super.save(comSystem);
	}
	@Transactional(readOnly = false)
	public AjaxJson preserve(ComSystem comSystem) {
		AjaxJson j =new AjaxJson();
		try{
			if (StringUtils.isNotBlank(comSystem.getSysCode())) {
				ComSystem com = new ComSystem();
				com.setSysCode(comSystem.getSysCode());
				int count = comSystemMapper.count(com);
				if (count > 0) {
					j.setSuccess(false);
					j.setMsg("制度编号重复");
					return j;
				}
			}
			if (StringUtils.isNotBlank(comSystem.getSysName())){
				ComSystem com = new ComSystem();
				com.setSysName(comSystem.getSysName());
				int count = comSystemMapper.count(com);
				if (count > 0) {
					j.setSuccess(false);
					j.setMsg("制度名称重复");
					return j;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		super.save(comSystem);
		j.setSuccess(true);
		j.setMsg("保存制度成功");
		return j;
	}
	
	@Transactional(readOnly = false)
	public void delete(ComSystem comSystem) {
		super.deleteByLogic(comSystem);
	}
	
}
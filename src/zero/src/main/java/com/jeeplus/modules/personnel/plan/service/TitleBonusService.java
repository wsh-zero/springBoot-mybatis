/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.service;

import java.util.List;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.personnel.plan.entity.GradeBonus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.plan.entity.TitleBonus;
import com.jeeplus.modules.personnel.plan.mapper.TitleBonusMapper;

/**
 * 职称Service
 * @author 王伟
 * @version 2019-03-14
 */
@Service
@Transactional(readOnly = true)
public class TitleBonusService extends CrudService<TitleBonusMapper, TitleBonus> {

	@Autowired
	private TitleBonusMapper titleBonusMapper;


	public TitleBonus get(String id) {
		return super.get(id);
	}
	
	public List<TitleBonus> findList(TitleBonus titleBonus) {
		return super.findList(titleBonus);
	}
	
	public Page<TitleBonus> findPage(Page<TitleBonus> page, TitleBonus titleBonus) {
		return super.findPage(page, titleBonus);
	}
	
	@Transactional(readOnly = false)
	public void save(TitleBonus titleBonus) {
		super.save(titleBonus);
	}
	@Transactional(readOnly = false)
	public AjaxJson preserve(TitleBonus titleBonus) {
		AjaxJson j = new AjaxJson();
		try{
			if (titleBonus.getIsNewRecord()) {
				if (StringUtils.isNotBlank(titleBonus.getName())) {
					TitleBonus bonus = new TitleBonus();
					bonus.setName(titleBonus.getName());
					int count = titleBonusMapper.count(bonus);
					if (count > 0) {
						j.setSuccess(false);
						j.setMsg("职称重复");
						return j;
					}
				}
			}else {
				if (StringUtils.isNotBlank(titleBonus.getName())) {
					TitleBonus bonus = new TitleBonus();
					bonus.setName(titleBonus.getName());
					TitleBonus title = titleBonusMapper.find(bonus);
					if (!title.getId().equals(titleBonus.getId())&&title!=null) {
						j.setSuccess(false);
						j.setMsg("职称重复");
						return j;
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		super.save(titleBonus);
		j.setSuccess(true);
		j.setMsg("保存职称成功");
		return j;
	}
	
	@Transactional(readOnly = false)
	public void delete(TitleBonus titleBonus) {
		super.deleteByLogic(titleBonus);
	}

	public List<TitleBonus> getTitle(){
		return titleBonusMapper.getTitle();
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.internal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.official.internal.entity.InternalOfficial;
import com.jeeplus.modules.official.internal.mapper.InternalOfficialMapper;

/**
 * 公司对内公文管理Service
 * @author chentao
 * @version 2019-04-03
 */
@Service
@Transactional(readOnly = true)
public class InternalOfficialService extends CrudService<InternalOfficialMapper, InternalOfficial> {

	public InternalOfficial get(String id) {
		return super.get(id);
	}
	
	public List<InternalOfficial> findList(InternalOfficial internalOfficial) {
		return super.findList(internalOfficial);
	}
	
	public Page<InternalOfficial> findPage(Page<InternalOfficial> page, InternalOfficial internalOfficial) {

		internalOfficial.setPage(null);
		int no = (page.getPageNo()-1) * page.getPageSize();
		page.setList(mapper.findListView(internalOfficial, no, page.getPageSize()));
		page.setCount(mapper.findListCount(internalOfficial));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(InternalOfficial internalOfficial) {
		super.save(internalOfficial);
		mapper.insertRecords(internalOfficial);
	}
	
	@Transactional(readOnly = false)
	public void delete(InternalOfficial internalOfficial) {
		super.delete(internalOfficial);
	}

}
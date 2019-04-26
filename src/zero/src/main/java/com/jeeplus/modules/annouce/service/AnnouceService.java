/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.annouce.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.time.DateFormatUtil;
import com.jeeplus.modules.annouce.entity.AnnouceRecord;
import com.jeeplus.modules.annouce.mapper.AnnouceRecordMapper;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.annouce.entity.Annouce;
import com.jeeplus.modules.annouce.mapper.AnnouceMapper;

/**
 * 发布公告管理Service
 * @author xy
 * @version 2019-02-28
 */
@Service
@Transactional(readOnly = true)
public class AnnouceService extends CrudService<AnnouceMapper, Annouce> {

	@Autowired
	private AnnouceRecordMapper annouceRecordMapper;

	@Autowired
	private AnnouceMapper annouceMapper;

	//判断当前用户是发布人还是阅读人
	public List findByUserId(String userid){
		return annouceMapper.findByUserId(userid);
	}

	public Annouce get(String id) {
		return super.get(id);
	}
	
	public List<Annouce> findList(Annouce annouce) {
		return super.findList(annouce);
	}
	
	public Page<Annouce> findPage(Page<Annouce> page, Annouce annouce) {
		return super.findPage(page, annouce);
	}

	public Page<Annouce> findRecvPage(Page<Annouce> page, Annouce annouce, String userId) {

		dataRuleFilter(annouce);
		annouce.setPage(page);
		page.setList(mapper.selectRecvList(annouce, userId, (page.getPageNo()-1) * page.getPageSize(), page.getPageSize()));
		page.setCount(mapper.selectRecvListCount(annouce, userId));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Annouce annouce) {
		//发布时间
		annouce.setPublishTime(new Date());
		//发布人
		annouce.setPublishPerson(UserUtils.getUser().getId());
		// 设置编号
		if (StringUtils.isBlank(annouce.getNumber())) {
			annouce.setNumber(generateNumber());
		}
		super.save(annouce);
		// 更新发送接受人记录
		annouceRecordMapper.deleteByAnnouceId(annouce.getId());
		if (annouce.getAnnouceRecordList().size() > 0){
			annouceRecordMapper.insertAll(annouce.getAnnouceRecordList());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Annouce annouce) {
		super.delete(annouce);
	}

	public Annouce getRecordList(Annouce annouce) {
		annouce.setAnnouceRecordList(annouceRecordMapper.findList(new AnnouceRecord(annouce)));
		return annouce;
	}

	/**
	 * 更新阅读状态
	 */
	@Transactional
	public void updateReadFlag(Annouce annouce) {
		AnnouceRecord annouceRecord = new AnnouceRecord(annouce);
		annouceRecord.setUser(annouceRecord.getCurrentUser());
		annouceRecord.setReadDate(new Date());
		annouceRecord.setReadFlag("1");
		annouceRecordMapper.updateReadFlag(annouceRecord);
	}

	/**
	 * 生成编号
	 */
	private String generateNumber() {

		Date date = new Date();
		String curDateStr = DateFormatUtil.formatDate("yyyyMMdd", date);
		int count = mapper.selectDateCount(date);
		String number = curDateStr + String.format("%04d", count + 1);
		return number;
	}
}
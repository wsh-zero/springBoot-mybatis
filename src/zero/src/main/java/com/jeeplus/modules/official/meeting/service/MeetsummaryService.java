/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.meeting.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.modules.annouce.entity.Annouce;
import com.jeeplus.modules.annouce.entity.AnnouceRecord;
import com.jeeplus.modules.official.meeting.entity.MeetsummmaryRecord;
import com.jeeplus.modules.official.meeting.mapper.MeetsummaryRecordMapper;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.official.meeting.entity.Meetsummary;
import com.jeeplus.modules.official.meeting.mapper.MeetsummaryMapper;

/**
 * 会议纪要管理Service
 * @author xy
 * @version 2019-03-04
 */
@Service
@Transactional(readOnly = true)
public class MeetsummaryService extends CrudService<MeetsummaryMapper, Meetsummary> {

	@Autowired
	private MeetsummaryMapper meetsummaryMapper;

	@Autowired
	private MeetsummaryRecordMapper meetsummaryRecordMapper;

	//判断当前用户是发布人还是阅读人
	public List findByUserId(String userid){
		return meetsummaryMapper.findByUserId(userid);
	}

	public Meetsummary get(String id) {
		return super.get(id);
	}
	
	public List<Meetsummary> findList(Meetsummary meetsummary) {
		return super.findList(meetsummary);
	}
	
	public Page<Meetsummary> findPage(Page<Meetsummary> page, Meetsummary meetsummary) {
		return super.findPage(page, meetsummary);
	}
	
	@Transactional(readOnly = false)
	public void save(Meetsummary meetsummary) {
		meetsummary.setInviteperson(UserUtils.getUser().getId());
		meetsummary.setInvitedate(new Date());
		super.save(meetsummary);
		// 更新发送接受人记录
		meetsummaryRecordMapper.deleteByMeetId(meetsummary.getId());
		if (meetsummary.getMeetsummmaryRecordList().size() > 0){
			meetsummaryRecordMapper.insertAll(meetsummary.getMeetsummmaryRecordList());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Meetsummary meetsummary) {
		super.delete(meetsummary);
	}

	public Meetsummary getRecordList(Meetsummary meetsummary) {
		meetsummary.setMeetsummmaryRecordList(meetsummaryRecordMapper.findList(new MeetsummmaryRecord(meetsummary)));
		return meetsummary;
	}

	/**
	 * 更新阅读状态
	 */
	@Transactional
	public void updateReadFlag(Meetsummary meetsummary) {
		MeetsummmaryRecord meetsummmaryRecord=new MeetsummmaryRecord(meetsummary);
		meetsummmaryRecord.setUser(meetsummmaryRecord.getCurrentUser());
		meetsummmaryRecord.setReadDate(new Date());
		meetsummmaryRecord.setReadFlag("1");
		meetsummaryRecordMapper.updateReadFlag(meetsummmaryRecord);
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.train.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.modules.personnel.train.entity.TrainRecord;
import com.jeeplus.modules.personnel.train.mapper.TrainRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.train.entity.Train;
import com.jeeplus.modules.personnel.train.mapper.TrainMapper;

/**
 * 培训管理Service
 * @author 王伟
 * @version 2019-02-19
 */
@Service
@Transactional(readOnly = true)
public class TrainService extends CrudService<TrainMapper, Train> {

	@Autowired
	private TrainRecordMapper trainRecordMapper;
	@Autowired
	private TrainMapper trainMapper;

	public Train get(String id) {
		return super.get(id);
	}

	/**
	 * 获取通知发送记录
	 * @param train
	 * @return
	 */
	public Train getRecordList(Train train) {
		train.setTrainRecordList(trainRecordMapper.findList(new TrainRecord(train)));
		return train;
	}
	public Page<Train> find(Page<Train> page, Train train) {
		train.setPage(page);
		page.setList(mapper.findList(train));
		return page;
	}
	public Page<TrainRecord> getTrainStaff(Page<TrainRecord> page, TrainRecord TrainRecord) {
		TrainRecord.setPage(page);
		page.setList(trainRecordMapper.getTrainStaff(TrainRecord));
		return page;
	}

	/**
	 * 获取我的培训列表信息
	 * @param page
	 * @param entity
     * @return
     */
	public Page<Train> findMyPage(Page<Train> page, Train train) {
		train.setPage(page);
		page.setList(trainMapper.findList(train));
		return page;
	}


	/**
	 * 获取通知数目
	 * @param train
	 * @return
	 */
	public Long findCount(Train train) {
		return mapper.findCount(train);
	}

	@Transactional(readOnly = false)
	public void save(Train train) {
		super.save(train);

		// 更新发送接受人记录
		trainRecordMapper.deleteByTrainId(train.getId());
		if (train.getTrainRecordList().size() > 0){
			trainRecordMapper.insertAll(train.getTrainRecordList());
		}
	}

	/**
	 * 更新阅读状态
	 */
	@Transactional(readOnly = false)
	public void updateReadFlag(Train train) {
		TrainRecord trainRecord = new TrainRecord(train);
		trainRecord.setUser(trainRecord.getCurrentUser());
		trainRecord.setReadDate(new Date());
		trainRecord.setReadFlag("1");
		trainRecordMapper.update(trainRecord);
	}

	/**
	 * 员工是否合格配置
	 * @param rank
	 * @return
     */
	@Transactional(readOnly = false)
	public void staffSave(TrainRecord record){
		trainRecordMapper.staffSave(record);
	}

	@Transactional(readOnly = false)
	public int count(Train rank) {
		return trainMapper.count(rank);
	}
}
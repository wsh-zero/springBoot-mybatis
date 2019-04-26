package com.jeeplus.modules.personnel.train.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.train.entity.TrainRecord;

import java.util.List;

/**
 * Created by DRYJKUIL on 2019/2/19.
 */
@MyBatisMapper
public interface TrainRecordMapper extends BaseMapper<TrainRecord>{
    /**
     * 插入通知记录
     * @param trainRecordList
     * @return
     */
    int insertAll(List<TrainRecord> trainRecordList);

     List<TrainRecord> getTrainStaff(TrainRecord trainRecord);

     TrainRecord get(String id);

     void staffSave(TrainRecord trainRecord);


    /**
     * 根据通知ID删除通知记录
     * @param trainId 通知ID
     * @return
     */
    int deleteByTrainId(String trainId);
}

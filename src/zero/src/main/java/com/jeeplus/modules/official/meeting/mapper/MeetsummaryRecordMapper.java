package com.jeeplus.modules.official.meeting.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.annouce.entity.AnnouceRecord;
import com.jeeplus.modules.official.meeting.entity.MeetsummmaryRecord;

import java.util.List;

/**
 * @author xy
 * @version
 */
@MyBatisMapper
public interface MeetsummaryRecordMapper extends BaseMapper<MeetsummmaryRecord> {

    /**
     * 插入通知记录
     * @param
     * @return
     */
    public int insertAll(List<MeetsummmaryRecord> meetsummmaryRecordList);

    /**
     * 根据通知ID删除通知记录
     * @param
     * @return
     */
    public int deleteByMeetId(String meetid);

    public int updateReadFlag(MeetsummmaryRecord meetsummmaryRecord);

}
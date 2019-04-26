/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.annouce.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.annouce.entity.AnnouceRecord;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;

import java.util.List;

/**
 * 阅读公告管理MAPPER接口
 * @author xy
 * @version 2019-02-28
 */
@MyBatisMapper
public interface AnnouceRecordMapper extends BaseMapper<AnnouceRecord> {

    /**
     * 插入通知记录
     * @param
     * @return
     */
    public int insertAll(List<AnnouceRecord> annouceRecordList);

    /**
     * 根据通知ID删除通知记录
     * @param
     * @return
     */
    public int deleteByAnnouceId(String annouceid);

    int updateReadFlag(AnnouceRecord annouceRecord);
	
}
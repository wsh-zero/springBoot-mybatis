/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.train.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.plan.entity.Rank;
import com.jeeplus.modules.personnel.train.entity.Train;

import java.util.List;

/**
 * 培训管理MAPPER接口
 * @author 王伟
 * @version 2019-02-19
 */
@MyBatisMapper
public interface TrainMapper extends BaseMapper<Train> {

    /**
     * 获取培训数目
     * @param oaNotify
     * @return
     */
    public Long findCount(Train train);

    int count(Train rank);

    public List<Train> findMyList(Train entity);

}
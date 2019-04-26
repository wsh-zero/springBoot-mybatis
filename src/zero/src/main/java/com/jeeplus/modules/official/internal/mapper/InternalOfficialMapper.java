/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.internal.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.official.internal.entity.InternalOfficial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公司对内公文管理MAPPER接口
 * @author chentao
 * @version 2019-04-03
 */
@MyBatisMapper
public interface InternalOfficialMapper extends BaseMapper<InternalOfficial> {

    void insertRecords(InternalOfficial official);

    Integer findListCount(InternalOfficial official);

    List<InternalOfficial> findListView(@Param("official") InternalOfficial official, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
}
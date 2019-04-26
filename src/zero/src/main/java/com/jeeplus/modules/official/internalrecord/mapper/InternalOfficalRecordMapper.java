/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.internalrecord.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.official.internalrecord.entity.InternalOfficalRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 内部公文接收状态MAPPER接口
 * @author chentao
 * @version 2019-04-03
 */
@MyBatisMapper
public interface InternalOfficalRecordMapper extends BaseMapper<InternalOfficalRecord> {

    void updateReaded(@Param("id") String id, @Param("user") String user, @Param("readed") String readed, @Param("date")Date date);
}
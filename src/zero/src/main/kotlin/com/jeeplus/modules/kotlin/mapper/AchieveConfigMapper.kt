package com.jeeplus.modules.kotlin.mapper

import com.jeeplus.core.persistence.BaseMapper
import com.jeeplus.core.persistence.annotation.MyBatisMapper
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveConfig

/**
 * 绩效配置MAPPER接口
 * @author zero
 * @version 2019-04-16
 */
@MyBatisMapper
interface AchieveConfigMapper : BaseMapper<AchieveConfig> {
    fun getIdAndName(): List<MapDto>
}
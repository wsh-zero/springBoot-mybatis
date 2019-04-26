/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.mapper

import com.jeeplus.core.persistence.BaseMapper
import com.jeeplus.core.persistence.annotation.MyBatisMapper
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveJudge

/**
 * 绩效评定表配置MAPPER接口
 * @author zero
 * @version 2019-04-18
 */
@MyBatisMapper
interface AchieveJudgeMapper : BaseMapper<AchieveJudge> {
    fun getIdAndName(): List<MapDto>
}
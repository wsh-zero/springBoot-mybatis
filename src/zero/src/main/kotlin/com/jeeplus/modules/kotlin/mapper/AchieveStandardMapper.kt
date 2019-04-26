/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.mapper

import com.jeeplus.core.persistence.BaseMapper
import com.jeeplus.core.persistence.annotation.MyBatisMapper
import com.jeeplus.modules.kotlin.entity.AchieveStandard

/**
 * 绩效评定标准MAPPER接口
 *
 * @author zero
 * @version 2019-04-16
 */
@MyBatisMapper
interface AchieveStandardMapper : BaseMapper<AchieveStandard> {
    //获取评定表考核标准内容,用于动态拼接表头
    fun getAchieveStandardNameByAchieveConfigId(achieveConfigId: String): List<String>
}
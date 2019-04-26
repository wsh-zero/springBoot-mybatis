/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.mapper

import com.jeeplus.core.persistence.BaseMapper
import com.jeeplus.core.persistence.annotation.MyBatisMapper
import com.jeeplus.modules.kotlin.entity.AchieveRule
import org.apache.ibatis.annotations.Param

/**
 * 绩效考核规则MAPPER接口
 * @author zero
 * @version 2019-04-22
 */
@MyBatisMapper
interface AchieveRuleMapper : BaseMapper<AchieveRule> {
    fun getTotalScoreByType(@Param("type") type: Int, @Param("id") id: String): String
}
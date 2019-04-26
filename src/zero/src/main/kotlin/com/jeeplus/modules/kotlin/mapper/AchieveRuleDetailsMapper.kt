/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.mapper

import com.jeeplus.core.persistence.TreeMapper
import com.jeeplus.core.persistence.annotation.MyBatisMapper
import com.jeeplus.modules.kotlin.entity.AchieveRuleDetails
import org.apache.ibatis.annotations.Param

/**
 * 绩效考核规则详情MAPPER接口
 * @author zero
 * @version 2019-04-26
 */
@MyBatisMapper
interface AchieveRuleDetailsMapper : TreeMapper<AchieveRuleDetails> {
    fun getAchieveRuleDetailsCountByAchieveRuleId(@Param("achieveRuleId") achieveRuleId: String, @Param("type") type: Int): Int
    fun editScoreByAchieveRuleId(@Param("achieveRuleId") achieveRuleId: String, @Param("score") score: String?, @Param("type") type: Int)
    fun isExistTypeByAchieveRuleId(@Param("achieveRuleId") achieveRuleId: String, @Param("type") type: Int): Boolean
    fun getChildren2(@Param("parentId") parentId: String, @Param("achieveRuleId") achieveRuleId: String): List<AchieveRuleDetails>
}
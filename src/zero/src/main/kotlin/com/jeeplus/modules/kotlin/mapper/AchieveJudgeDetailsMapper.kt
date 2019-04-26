/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.mapper

import com.jeeplus.core.persistence.TreeMapper
import com.jeeplus.core.persistence.annotation.MyBatisMapper
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveJudgeDetails
import com.jeeplus.modules.kotlin.vo.AchieveJudgeDetails1VO
import com.jeeplus.modules.kotlin.vo.AchieveJudgeDetails2VO
import org.apache.ibatis.annotations.Param

/**
 * 绩效评定详情MAPPER接口
 * @author zero
 * @version 2019-04-19
 */
@MyBatisMapper
interface AchieveJudgeDetailsMapper : TreeMapper<AchieveJudgeDetails> {
    fun getChildren2(@Param("parentId") parentId: String, @Param("achieveJudgeId") achieveJudgeId: String?): List<AchieveJudgeDetails>?
    fun deleteByAchieveJudgeId(@Param("achieveJudgeId") achieveJudgeId: String)
    fun getIdAndNameByAchieveJudgeId(achieveJudgeId: String): List<MapDto>
    fun isExistAchieveJudgeId(achieveJudgeId: String): Boolean
//    fun getAchieveJudgeDetails1(achieveRuleId: String): List<AchieveJudgeDetails1VO>
//    fun getAchieveJudgeDetails2(achieveRuleId: String): List<AchieveJudgeDetails2VO>
    fun getAchieveJudgeDetailsByAchieveJudgeId(achieveJudgeId: String): AchieveJudgeDetails?
}
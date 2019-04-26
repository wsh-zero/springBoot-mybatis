/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.service

import com.jeeplus.common.utils.IdGen
import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.service.TreeService
import com.jeeplus.modules.kotlin.entity.AchieveRuleDetails
import com.jeeplus.modules.kotlin.mapper.AchieveRuleDetailsMapper
import com.jeeplus.modules.kotlin.utils.Const
import com.jeeplus.modules.kotlin.utils.Utils
import com.jeeplus.modules.kotlin.vo.AchieveJudgeDetails2VO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 绩效考核规则详情Service
 * @author zero
 * @version 2019-04-26
 */
@Service
@Transactional(readOnly = true)
open class AchieveRuleDetailsService : TreeService<AchieveRuleDetailsMapper, AchieveRuleDetails>() {

    @Autowired
    lateinit var achieveRuleDetailsMapper: AchieveRuleDetailsMapper
    @Autowired
    lateinit var achieveRuleService: AchieveRuleService
    @Autowired
    lateinit var achieveJudgeDetailsService: AchieveJudgeDetailsService

    override fun get(id: String): AchieveRuleDetails? {
        return super.get(id)
    }

    open fun getChildren2(parentId: String, achieveRuleId: String): List<AchieveJudgeDetails2VO<AchieveRuleDetails>> {
        val children2 = achieveRuleDetailsMapper.getChildren2(parentId, achieveRuleId)
        val result: MutableList<AchieveJudgeDetails2VO<AchieveRuleDetails>> = mutableListOf()
        for (vo in children2) {
            val get = achieveRuleService.get(achieveRuleId)
            val achieveJudgeDetails = achieveJudgeDetailsService.getAchieveJudgeDetailsByAchieveJudgeId(get.achieveJudgeId!!)
            val initVo = AchieveJudgeDetails2VO<AchieveRuleDetails>()
            achieveJudgeDetails?.let {
                initVo.achieveA = it.achieveA
                initVo.achieveB = it.achieveB
                initVo.achieveC = it.achieveC
                initVo.achieveD = it.achieveD
            }
            initVo.id = vo.id
            initVo.parent = vo.parent
            initVo.parentIds = vo.parentIds
            initVo.sort = vo.sort
            initVo.planSuccAmount = vo.planSuccAmount
            initVo.planSuccTime = vo.planSuccTime
            initVo.score = vo.score
            initVo.type = vo.type
            initVo.taskContent = vo.taskContent
            result.add(initVo)
        }
        return result
    }

    override fun findList(achieveRuleDetails: AchieveRuleDetails): List<AchieveRuleDetails> {
        if (StringUtils.isNotBlank(achieveRuleDetails.getParentIds())) {
            achieveRuleDetails.setParentIds("," + achieveRuleDetails.getParentIds() + ",")
        }
        return super.findList(achieveRuleDetails)
    }

    @Transactional(readOnly = false)
    override fun save(entity: AchieveRuleDetails) {
        val type = entity.type
        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        if (entity.parent == null || StringUtils.isBlank(entity.parentId)
                || "0" == entity.parentId) {
            entity.parent = null
        } else {
            entity.parent = super.get(entity.parentId)
        }
        if (entity.parent == null) {
            entity.parent = AchieveRuleDetails()
            entity.parent!!.id = "0"
            entity.parent!!.parentIds = StringUtils.EMPTY
        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        val oldParentIds = entity.parentIds

        // 设置新的父节点串
        entity.parentIds = entity.parent!!.parentIds + entity.parent!!.id + ","

        // 保存或更新实体
        if (entity.isNewRecord) {
            if (type == Const.AchieveRuleDetails.TYPE_ZERO) {
                //删除以前的
//            delByAchieveRuleId(achieveRuleDetails.achieveRuleId!!)
//            val achieveJudgeDetailsIds = achieveRuleDetails.achieveJudgeDetailsIds
//            achieveJudgeDetailsIds?.let {
//                val size = it.size
//                val scoreStr = achieveRuleService.getTotalScoreByType(Const.AchieveRuleDetails.TYPE_ZERO, achieveRuleDetails.achieveRuleId!!)
//                val calculatedAverage = Utils.calculatedAverage(scoreStr, size)
//                for (achieveJudgeDetailsId in it) {
//                    achieveRuleDetails.id = IdGen.uuid()
//                    achieveRuleDetails.achieveJudgeDetailsId = achieveJudgeDetailsId
//                    achieveRuleDetails.score = calculatedAverage.toString()
//                    mapper.insert(achieveRuleDetails)
//                }
//            }
            } else {
                entity.preInsert()
                //通过achieveRuleId获取数量
                val size = achieveRuleDetailsMapper.getAchieveRuleDetailsCountByAchieveRuleId(entity.achieveRuleId!!, type!!) + 1
                //判断是否有临时任务
                val existType = achieveRuleDetailsMapper.isExistTypeByAchieveRuleId(entity.achieveRuleId!!, Const.AchieveRuleDetails.TYPE_THREE)
                var totalScore1 = entity.totalScore1!!
                val totalScore3 = entity.totalScore3!!
                if (!existType) {
                    if (Const.AchieveRuleDetails.TYPE_THREE != type) {
                        //不是临时任务
                        val valueOf1 = Integer.valueOf(totalScore1)
                        val valueOf3 = Integer.valueOf(totalScore3)
                        totalScore1 = (valueOf1 + valueOf3).toString()
                    } else {
                        val size1 = achieveRuleDetailsMapper.getAchieveRuleDetailsCountByAchieveRuleId(entity.achieveRuleId!!, Const.AchieveRuleDetails.TYPE_ONE)
                        achieveRuleDetailsMapper.editScoreByAchieveRuleId(entity.achieveRuleId!!, Utils.calculatedAverage(totalScore1, size1), Const.AchieveRuleDetails.TYPE_ONE)
                    }
                }
                var calculatedAverage: String? = null
                when (type) {
                    Const.AchieveRuleDetails.TYPE_ONE, Const.AchieveRuleDetails.TYPE_TWO -> {
                        calculatedAverage = Utils.calculatedAverage(totalScore1, size)
                    }
                    Const.AchieveRuleDetails.TYPE_THREE -> {
                        calculatedAverage = Utils.calculatedAverage(totalScore3, size)
                    }
                }
                entity.score = calculatedAverage
                entity.id = IdGen.uuid()
                mapper.insert(entity)
                achieveRuleDetailsMapper.editScoreByAchieveRuleId(entity.achieveRuleId!!, calculatedAverage, type)
            }
        } else {
            entity.preUpdate()
            mapper.update(entity)
        }

        // 更新子节点 parentIds
        val o = AchieveRuleDetails()
        o.parentIds = "%," + entity.id + ",%"
        val list = mapper.findByParentIdsLike(o)
        for (e in list) {
            if (e.parentIds != null && oldParentIds != null) {
                e.parentIds = e.parentIds.replace(oldParentIds, entity.getParentIds())
                preUpdateChild(entity, e)
                mapper.updateParentIds(e)
            }
        }


    }

    @Transactional(readOnly = false)
    override fun delete(achieveRuleDetails: AchieveRuleDetails) {
        var type = achieveRuleDetails.type
        val get = achieveRuleService.get(achieveRuleDetails.achieveRuleId!!)
        super.delete(achieveRuleDetails)
        if (type == Const.AchieveRuleDetails.TYPE_ZERO) {
        } else {
            val size = achieveRuleDetailsMapper.getAchieveRuleDetailsCountByAchieveRuleId(get.id, type!!)
            /**
             * 1.删除常规任务/新增任务
             */
            var totalScore1 = get.totalScore1
            val achieveRuleId = get.id
            val totalScore3 = get.totalScore3
            val existType = achieveRuleDetailsMapper.isExistTypeByAchieveRuleId(achieveRuleId, Const.AchieveRuleDetails.TYPE_THREE)
            var calculatedAverage: String? = null
            /**
             * 1.删除常规任务/新增任务
             * 2.删除临时任务
             */
            when (type) {
                Const.AchieveRuleDetails.TYPE_ONE, Const.AchieveRuleDetails.TYPE_TWO -> {
                    //判断是否有临时任务
                    if (!existType) {
                        val valueOf1 = Integer.valueOf(totalScore1)
                        val valueOf3 = Integer.valueOf(totalScore3)
                        totalScore1 = (valueOf1 + valueOf3).toString()
                    } else {
                        val size1 = achieveRuleDetailsMapper.getAchieveRuleDetailsCountByAchieveRuleId(achieveRuleId, Const.AchieveRuleDetails.TYPE_THREE)
                        achieveRuleDetailsMapper.editScoreByAchieveRuleId(achieveRuleId, Utils.calculatedAverage(totalScore3!!, size1), Const.AchieveRuleDetails.TYPE_THREE)
                    }
                    calculatedAverage = Utils.calculatedAverage(totalScore1!!, size)
                }
                Const.AchieveRuleDetails.TYPE_THREE -> {
                    if (!existType) {
                        val valueOf1 = Integer.valueOf(totalScore1)
                        val valueOf3 = Integer.valueOf(totalScore3)
                        totalScore1 = (valueOf1 + valueOf3).toString()
                    }
                    val size1 = achieveRuleDetailsMapper.getAchieveRuleDetailsCountByAchieveRuleId(achieveRuleId, Const.AchieveRuleDetails.TYPE_ONE)
                    calculatedAverage = Utils.calculatedAverage(totalScore1!!, size1)
                    type = Const.AchieveRuleDetails.TYPE_ONE
                }
            }
            achieveRuleDetailsMapper.editScoreByAchieveRuleId(achieveRuleId, calculatedAverage, type)
        }
    }

}
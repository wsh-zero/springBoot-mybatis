package com.jeeplus.modules.kotlin.service

import com.jeeplus.common.json.AjaxJson
import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.service.TreeService
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveJudgeDetails
import com.jeeplus.modules.kotlin.mapper.AchieveJudgeDetailsMapper
import com.jeeplus.modules.kotlin.utils.Const
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 绩效评定详情Service
 * @author zero
 * @version 2019-04-19
 */
@Service
@Transactional(readOnly = true)
open class AchieveJudgeDetailsService : TreeService<AchieveJudgeDetailsMapper, AchieveJudgeDetails>() {
    @Autowired
    lateinit var achieveJudgeDetailsMapper: AchieveJudgeDetailsMapper

    override fun get(id: String): AchieveJudgeDetails? {
        return super.get(id)
    }

    open fun getAchieveJudgeDetailsByAchieveJudgeId(achieveJudgeId: String): AchieveJudgeDetails? {
        return achieveJudgeDetailsMapper.getAchieveJudgeDetailsByAchieveJudgeId(achieveJudgeId)
    }

    open fun getChildren2(parentId: String, achieveJudgeId: String?): List<AchieveJudgeDetails>? {
        return achieveJudgeDetailsMapper.getChildren2(parentId, achieveJudgeId)
    }

    open fun deleteByAchieveJudgeId(achieveJudgeId: String) {
        achieveJudgeDetailsMapper.deleteByAchieveJudgeId(achieveJudgeId)
    }

    open fun getIdAndNameByAchieveJudgeId(achieveJudgeId: String): List<MapDto> {
        return achieveJudgeDetailsMapper.getIdAndNameByAchieveJudgeId(achieveJudgeId)
    }


    override fun findList(achieveJudgeDetails: AchieveJudgeDetails): List<AchieveJudgeDetails> {
        if (StringUtils.isNotBlank(achieveJudgeDetails.getParentIds())) {
            achieveJudgeDetails.setParentIds("," + achieveJudgeDetails.getParentIds() + ",")
        }
        return super.findList(achieveJudgeDetails)
    }

    @Transactional(readOnly = false)
    open fun save2(entity: AchieveJudgeDetails): AjaxJson? {
        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        if (entity.parent == null || StringUtils.isBlank(entity.parentId)
                || "0" == entity.parentId) {
            entity.parent = null
        } else {
            entity.parent = super.get(entity.parentId)
        }
        if (entity.parent == null) {
            entity.parent = AchieveJudgeDetails()
            entity.parent!!.id = "0"
            entity.parent!!.parentIds = StringUtils.EMPTY
        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        val oldParentIds = entity.parentIds

        // 设置新的父节点串
        entity.parentIds = entity.parent!!.parentIds + entity.parent!!.id + ","

        // 保存或更新实体
        if (entity.isNewRecord) {
            entity.preInsert()
            if (achieveJudgeDetailsMapper.isExistAchieveJudgeId(Const.AchieveJudge.TYPE_ZERO)) {
                val j = AjaxJson()
                j.isSuccess = false
                j.msg = "能力维度详情只能添加一条"
                return j
            }
            mapper.insert(entity)
        } else {
            entity.preUpdate()
            mapper.update(entity)
        }

        // 更新子节点 parentIds
        val o = AchieveJudgeDetails()
        o.parentIds = "%," + entity.id + ",%"
        val list = mapper.findByParentIdsLike(o)
        for (e in list) {
            if (e.parentIds != null && oldParentIds != null) {
                e.parentIds = e.parentIds.replace(oldParentIds, entity.getParentIds())
                preUpdateChild(entity, e)
                mapper.updateParentIds(e)
            }
        }
        return null
    }

    @Transactional(readOnly = false)
    override fun delete(achieveJudgeDetails: AchieveJudgeDetails) {
        super.delete(achieveJudgeDetails)
    }

}
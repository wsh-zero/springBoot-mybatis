/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.service

import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.persistence.Page
import com.jeeplus.core.service.CrudService
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveJudge
import com.jeeplus.modules.kotlin.mapper.AchieveJudgeMapper
import com.jeeplus.modules.sys.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 绩效评定表配置Service
 * @author zero
 * @version 2019-04-18
 */
@Service
@Transactional(readOnly = true)
open class AchieveJudgeService : CrudService<AchieveJudgeMapper, AchieveJudge>() {

    @Autowired
    lateinit var userMapper: UserMapper
    @Autowired
    lateinit var achieveJudgeMapper: AchieveJudgeMapper

    @Autowired
    lateinit var achieveJudgeDetailsService: AchieveJudgeDetailsService

    open fun getIdAndName(): List<MapDto> {
        return achieveJudgeMapper.getIdAndName()
    }

    override fun get(id: String): AchieveJudge {
        return super.get(id)
    }

    override fun findList(achieveJudge: AchieveJudge): List<AchieveJudge> {
        return super.findList(achieveJudge)
    }

    override fun findPage(page: Page<AchieveJudge>, achieveJudge: AchieveJudge): Page<AchieveJudge> {
        val findPage = super.findPage(page, achieveJudge)
        findPage.list.map { it.createBy = userMapper.get(it.createBy.id) }
        return findPage
    }

    @Transactional(readOnly = false)
    override fun save(achieveJudge: AchieveJudge) {
        if (achieveJudge.isNewRecord || StringUtils.isNotBlank(achieveJudge.temporaryId)) {
            achieveJudge.preInsert()
            achieveJudge.id = achieveJudge.temporaryId
            mapper.insert(achieveJudge)
        } else {
            achieveJudge.preUpdate()
            mapper.update(achieveJudge)
        }
    }

    @Transactional(readOnly = false)
    override fun delete(achieveJudge: AchieveJudge) {
        super.delete(achieveJudge)
        achieveJudgeDetailsService.deleteByAchieveJudgeId(achieveJudge.id)
    }

}
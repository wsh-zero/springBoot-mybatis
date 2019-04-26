/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.service

import com.jeeplus.core.persistence.Page
import com.jeeplus.core.service.CrudService
import com.jeeplus.modules.kotlin.entity.AchieveStandard
import com.jeeplus.modules.kotlin.mapper.AchieveStandardMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 绩效评定标准Service
 * @author zero
 * @version 2019-04-16
 */
@Service
@Transactional(readOnly = true)
open class AchieveStandardService : CrudService<AchieveStandardMapper, AchieveStandard>() {

    @Autowired
    lateinit var achieveStandardMapper: AchieveStandardMapper

    override fun get(id: String): AchieveStandard {
        return super.get(id)
    }

    open fun getAchieveStandardNameByAchieveConfigId(achieveConfigId: String): List<String> {
        return achieveStandardMapper.getAchieveStandardNameByAchieveConfigId(achieveConfigId)
    }

    override fun findList(achieveStandard: AchieveStandard): List<AchieveStandard> {
        return super.findList(achieveStandard)
    }

    override fun findPage(page: Page<AchieveStandard>, achieveStandard: AchieveStandard): Page<AchieveStandard> {
        page.orderBy="score desc"
        return super.findPage(page, achieveStandard)
    }

    @Transactional(readOnly = false)
    override fun save(achieveStandard: AchieveStandard) {
        super.save(achieveStandard)
    }

    @Transactional(readOnly = false)
    override fun delete(achieveStandard: AchieveStandard) {
        super.delete(achieveStandard)
    }

}
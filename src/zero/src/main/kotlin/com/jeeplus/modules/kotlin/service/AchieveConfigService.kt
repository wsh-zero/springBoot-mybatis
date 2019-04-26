/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.service

import com.jeeplus.core.persistence.Page
import com.jeeplus.core.service.CrudService
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveConfig
import com.jeeplus.modules.kotlin.mapper.AchieveConfigMapper
import com.jeeplus.modules.sys.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 绩效配置Service
 * @author zero
 * @version 2019-04-16
 */
@Service
@Transactional(readOnly = true)
open class AchieveConfigService : CrudService<AchieveConfigMapper, AchieveConfig>() {

    @Autowired
    lateinit var userMapper: UserMapper
    @Autowired
    lateinit var achieveConfigMapper: AchieveConfigMapper

    override fun get(id: String): AchieveConfig {
        return super.get(id)
    }

    open fun getIdAndName(): List<MapDto> {
        return achieveConfigMapper.getIdAndName()
    }

    override fun findList(achieveConfig: AchieveConfig): List<AchieveConfig> {
        return super.findList(achieveConfig)
    }

    override fun findPage(page: Page<AchieveConfig>, achieveConfig: AchieveConfig): Page<AchieveConfig> {
        val findPage = super.findPage(page, achieveConfig)
        findPage.list.map { it.createBy = userMapper.get(it.createBy.id) }
        return findPage
    }

    @Transactional(readOnly = false)
    override fun save(achieveConfig: AchieveConfig) {
        super.save(achieveConfig)
    }

    @Transactional(readOnly = false)
    override fun delete(achieveConfig: AchieveConfig) {
        super.delete(achieveConfig)
    }

}
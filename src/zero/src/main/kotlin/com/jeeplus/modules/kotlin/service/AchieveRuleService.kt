/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.service

import com.jeeplus.core.persistence.Page
import com.jeeplus.core.service.CrudService
import com.jeeplus.modules.kotlin.entity.AchieveRule
import com.jeeplus.modules.kotlin.mapper.AchieveRuleMapper
import com.jeeplus.modules.sys.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 绩效考核规则Service
 * @author zero
 * @version 2019-04-22
 */
@Service
@Transactional(readOnly = true)
open class AchieveRuleService : CrudService<AchieveRuleMapper, AchieveRule>() {

    @Autowired
    lateinit var userMapper: UserMapper
    @Autowired
    lateinit var achieveRuleMapper: AchieveRuleMapper

    override fun get(id: String): AchieveRule {
        return super.get(id)
    }

    override fun findList(achieveRule: AchieveRule): List<AchieveRule> {
        return super.findList(achieveRule)
    }

    open fun getTotalScoreByType(type: Int, id: String): String {
        return achieveRuleMapper.getTotalScoreByType(type, id)
    }

    override fun findPage(page: Page<AchieveRule>, achieveRule: AchieveRule): Page<AchieveRule> {
        val findPage = super.findPage(page, achieveRule)
        findPage.list.map { it.createBy = userMapper.get(it.createBy.id) }
        return findPage
    }

    @Transactional(readOnly = false)
    override fun save(achieveRule: AchieveRule) {
        super.save(achieveRule)
    }

    @Transactional(readOnly = false)
    override fun delete(achieveRule: AchieveRule) {
        super.delete(achieveRule)
    }

}
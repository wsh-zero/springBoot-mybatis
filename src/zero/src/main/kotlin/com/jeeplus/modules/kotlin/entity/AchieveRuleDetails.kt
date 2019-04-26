/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.jeeplus.core.persistence.TreeEntity
import java.util.*

/**
 * 绩效考核规则详情Entity
 * @author zero
 * @version 2019-04-26
 */
open class AchieveRuleDetails : TreeEntity<AchieveRuleDetails> {
    var achieveRuleId: String? = null        // 考核规则编号
    var score: String? = null        // 分值
    var achieveJudgeDetailsId: String? = null        // 考核指标编号
    var achieveJudgeDetailsIds: List<String>? = null
    var type: Int? = null        // 类型(0非任务维度 1常规任务 2新增任务 3临时任务)
    var taskContent: String? = null        // 任务内容(任务维度)
    var planSuccAmount: String? = null        // 计划完成量
    @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var planSuccTime: Date? = null        // 计划完成时间
    @get:JsonIgnore
    var totalScore0: String? = null        // 非任务维度总分值
    @get:JsonIgnore
    var totalScore1: String? = null        // 新增/常规任务总分值(任务维度)
    @get:JsonIgnore
    var totalScore3: String? = null        // 临时任务总分值(任务维度)
    @get:JsonIgnore
    var ruleName: String? = null
    @get:JsonIgnore
    var ruleNumber: String? = null


    constructor() : super() {}

    constructor(id: String) : super(id) {}

    override fun getParent(): AchieveRuleDetails? {
        return parent
    }

    override fun setParent(parent: AchieveRuleDetails?) {
        this.parent = parent

    }

    override fun getParentId(): String {
        return if (parent != null && parent.getId() != null) parent.getId() else "0"
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
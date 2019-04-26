/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.entity


import com.jeeplus.core.persistence.DataEntity

/**
 * 绩效考核规则Entity
 * @author zero
 * @version 2019-04-22
 */
class AchieveRule : DataEntity<AchieveRule> {
    var number: String? = null        // 评分编号
    var name: String? = null        // 评分表名称
    var achieveObjId: String? = null        // 对象编号
    var achieveJudgeId: String? = null        // 评定表编号(当等于0时,代表任务维度)
    var assessor: Int? = null        // 考核人(1,直接上级 2,直接下级 3,各部门同级)
    var assessorCycle: Int? = null        // 考核周期(1月 2季 3年)
    var state: Int? = null        // 状态(0禁用 1启用)
    var totalScore0: String? = null        // 非任务维度总分值
    var totalScore1: String? = null        // 新增/常规任务总分值(任务维度)
    var totalScore3: String? = null        // 临时任务总分值(任务维度)

    constructor() : super() {}

    constructor(id: String) : super(id) {}

    companion object {

        private val serialVersionUID = 1L
    }

}
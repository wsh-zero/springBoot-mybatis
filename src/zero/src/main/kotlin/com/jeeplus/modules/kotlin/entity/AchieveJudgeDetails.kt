/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.jeeplus.core.persistence.TreeEntity

/**
 * 绩效评定详情Entity
 * @author zero
 * @version 2019-04-19
 */
open class AchieveJudgeDetails : TreeEntity<AchieveJudgeDetails> {
    var achieveJudgeId: String? = null        // 评定表编号
    var achieveA: String? = null        // A级
    var achieveB: String? = null        // B级
    var achieveC: String? = null        // C级
    var achieveD: String? = null        // D级
    @get:JsonIgnore
    var temporaryId: String? = null        // 临时id


    constructor() : super() {}

    constructor(id: String) : super(id) {}

    override fun getParent(): AchieveJudgeDetails? {
        return parent
    }

    override fun setParent(parent: AchieveJudgeDetails?) {
        this.parent = parent

    }

    override fun getParentId(): String {
        return if (parent != null && parent.getId() != null) parent.getId() else "0"
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
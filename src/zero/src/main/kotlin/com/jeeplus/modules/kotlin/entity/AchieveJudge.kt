/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.entity


import com.fasterxml.jackson.annotation.JsonIgnore
import com.jeeplus.core.persistence.DataEntity

/**
 * 绩效评定表配置Entity
 * @author zero
 * @version 2019-04-18
 */
class AchieveJudge : DataEntity<AchieveJudge> {
    var name: String? = null        // 考核标准
    var achieveConfigId: String? = null        // 考核标准编号
    @get:JsonIgnore
    var temporaryId: String? = null        // 临时id

    constructor() : super() {}

    constructor(id: String) : super(id) {}

    companion object {

        private val serialVersionUID = 1L
    }

}
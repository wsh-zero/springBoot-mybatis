/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.entity


import com.jeeplus.core.persistence.DataEntity

/**
 * 绩效评定标准Entity
 *
 * @author zero
 * @version 2019-04-16
 */
class AchieveStandard : DataEntity<AchieveStandard> {
    var achieveConfigId: String? = null        // 绩效配置编号
    var name: String? = null        // 考核标准
    var score: Int? = null        // 分值(百分比),存0-100整数

    constructor() : super() {}

    constructor(id: String) : super(id) {}

    companion object {

        private val serialVersionUID = 1L
    }

}
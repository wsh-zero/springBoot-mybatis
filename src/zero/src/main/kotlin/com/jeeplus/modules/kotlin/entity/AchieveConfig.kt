/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.entity


import com.jeeplus.core.persistence.DataEntity

/**
 * 绩效配置Entity
 * @author zero
 * @version 2019-04-16
 */
class AchieveConfig : DataEntity<AchieveConfig> {
    var name: String? = null        // 考核标准
    var tab: Int = 1        // 选项卡 (1第一个 2第二个 3第三个 4第四个)

    constructor() : super() {}

    constructor(id: String) : super(id) {}

    companion object {

        private val serialVersionUID = 1L
    }

}
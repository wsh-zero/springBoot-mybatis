/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.entity


import com.jeeplus.core.persistence.DataEntity
import com.jeeplus.modules.kotlin.dto.MapDto

/**
 * 绩效对象配置Entity
 * @author zero
 * @version 2019-04-17
 */
class AchieveObj : DataEntity<AchieveObj> {
    var rankId: String? = null        // 职级编号
    var rankName: String? = null
    var dept: List<MapDto>? = null
    var deptIdList: List<String>? = null
    var name: String? = null        // 分类名称

    constructor() : super() {}

    constructor(id: String) : super(id) {}

    companion object {

        private val serialVersionUID = 1L
    }

}
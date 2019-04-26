package com.jeeplus.modules.kotlin.vo

import java.util.*

class AchieveJudgeDetails2VO<T> {
    var id: String? = null
    var parent: T? = null    // 父级编号
    var parentIds: String? = null // 所有父级编号
    var sort: Int? = null // 排序
    var score: String? = null
    var achieveA: String? = null
    var achieveB: String? = null
    var achieveC: String? = null
    var achieveD: String? = null
    var type: Int? = null
    var taskContent: String? = null
    var planSuccAmount: String? = null
    var planSuccTime: Date? = null
}
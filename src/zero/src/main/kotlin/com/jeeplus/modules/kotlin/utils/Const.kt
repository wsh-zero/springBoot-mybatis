package com.jeeplus.modules.kotlin.utils

interface Const {
    interface AchieveRuleDetails {
        companion object {
            //'类型(0非任务维度 1常规任务 2新增任务 3临时任务)'
            const val TYPE_ZERO = 0
            const val TYPE_ONE = 1
            const val TYPE_TWO = 2
            const val TYPE_THREE = 3
        }
    }
    interface AchieveJudge {
        companion object {
            //任务维度
            const val TYPE_ZERO = "0"
        }
    }
}
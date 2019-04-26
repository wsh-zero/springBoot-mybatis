package com.jeeplus.modules.kotlin.utils

import com.jeeplus.modules.monitor.entity.Task
import org.quartz.DisallowConcurrentExecution

@DisallowConcurrentExecution
class AchieveWeekTask : Task() {

    override fun run() {
        println("周执行。")
    }

}

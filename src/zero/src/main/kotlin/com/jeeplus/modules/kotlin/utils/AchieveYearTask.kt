package com.jeeplus.modules.kotlin.utils

import com.jeeplus.modules.monitor.entity.Task
import org.quartz.DisallowConcurrentExecution

@DisallowConcurrentExecution
class AchieveYearTask : Task() {

    override fun run() {
        println("年执行。")
    }

}

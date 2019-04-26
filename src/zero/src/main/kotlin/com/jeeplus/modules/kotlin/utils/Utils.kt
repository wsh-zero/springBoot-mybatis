package com.jeeplus.modules.kotlin.utils

import java.util.regex.Pattern

object Utils {
    /**
     * 计算平均数,并四舍五入
     * scoreStr 分值
     * size 条数
     */
    fun calculatedAverage(scoreStr: String, size: Int): String {
        val score = Integer.valueOf(scoreStr)
        val fl = score.toFloat() / size
        if (isInteger(fl.toString())) return fl.toString()
        return String.format("%.1f", score.toFloat() / size)
    }

    /*
      * 判断是否为整数
      * @param str 传入的字符串
      * @return 是整数返回true,否则返回false
    */
    fun isInteger(str: String): Boolean {
        val pattern = Pattern.compile("^[-\\+]?[\\d]*$")
        return pattern.matcher(str).matches()
    }
}
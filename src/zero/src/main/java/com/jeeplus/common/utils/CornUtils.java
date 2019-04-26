package com.jeeplus.common.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Java日期转换成Cron日期表达式工具类
 * @author ww
 */

public class CornUtils {
    /*
          *  
          * @param date 
          * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss 
          * @return 
          */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /*** 
          * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014" 
          * @param date  : 时间点 
          * @return 
          */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd * ? *";
        return formatDateByPattern(date, dateFormat);
    }


}

package com.wsh.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final DateTimeFormatter FTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String convertTimeToString(Long time, DateTimeFormatter formatter) {
        return formatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    public static Timestamp getCurrentTime() {
        return new Timestamp(getCurrentTimestamp());
    }

    /**
     * 明天0点和现在时间相差多少秒
     */
    public static long differSecond() {
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        int year = now.getYear();
        Month nowMonth = now.getMonth();
        int dayOfMonth = now.getDayOfMonth();
        LocalDateTime max = LocalDateTime.of(year, nowMonth.getValue(), dayOfMonth, 0, 0, 0);
        return (Timestamp.valueOf(max).getTime() - getCurrentTimestamp()) / 1000;
    }
}

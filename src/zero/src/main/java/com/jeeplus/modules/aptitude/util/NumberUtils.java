package com.jeeplus.modules.aptitude.util;

import com.jeeplus.common.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {

    public static boolean isPureDigital(String string) {
        if (StringUtils.isBlank(string))
            return false;
        String regEx1 = "\\d+";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
}

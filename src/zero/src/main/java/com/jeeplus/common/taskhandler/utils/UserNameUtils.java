package com.jeeplus.common.taskhandler.utils;

import com.jeeplus.modules.sys.entity.User;
import java.util.List;

/**
 * 用户名工具类
 * @author chentao
 * @version 2019-04-03
 */
public class UserNameUtils {

    public static String concatUserNames(List<User> users) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            sb.append(users.get(i).getLoginName());
            if (i != users.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}

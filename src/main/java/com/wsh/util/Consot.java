package com.wsh.util;

/**
 * 常量接口
 */
public interface Consot {
    String ALL_ZERO_UUID = "00000000-0000-0000-0000-000000000000";
    String LOGIN_PACKAGE_NAME = "com.wsh.zero.controller.base.LoginController.login";
    String USER_NAME = "游客";
    Integer POWER_TYPE = 2;//目录
    /**
     * 权限最上级
     */
    String POWER_MIN_ID = "0";
    /**
     * 容联云验证码redis
     */
    String RLY = "rlySms";
    /**
     * 短信验证码手机号获取次数(用来控制同一个手机一天最多发送几次)
     */
    String GET_LIMIT = "getLimit";
    int SMS_LIMIT = 3;
}

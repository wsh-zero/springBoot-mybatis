package com.wsh.util;

/**
 * 常量接口
 */
public interface Consot {
    String ALL_ZERO_UUID = "00000000-0000-0000-0000-000000000000";
    String LOGIN_PACKAGE_NAME = "com.wsh.zero.controller.base.LoginController.login";
    String USER_NAME = "游客";
    /**
     * 状态(0必须拥有才能访问,1游客可以访问)
     */
    Integer POWER_STATE_ZERO = 0;
    Integer POWER_STATE_ONE = 1;
    /**
     * 状态(0必须拥有才能访问,1游客可以访问)
     */
    Integer POWER_TYPE = 2;//目录
    /**
     * 权限最上级
     */
    String POWER_MIN_ID = "0";
}

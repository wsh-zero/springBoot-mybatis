package com.jeeplus.common.taskhandler.exception;

/**
 * 处理者配置异常类
 * @author chentao
 * @version 2019-04-02
 */
public class ErrorHandlerConfigException extends Exception {

    public ErrorHandlerConfigException(String conf) {
        super("处理配置异常：" + conf);
    }
}

package com.junple.jmail.client;

import com.junple.jmail.handler.MailHandler;
import com.junple.jmail.properties.MailProperties;

import java.util.Date;

/**
 * 邮件客户端接口
 * @author chentao
 * @version 2019-04-03
 */
public interface MailClient {

    /**
     * 运行邮件客户端
     */
    void start();

    /**
     * 停止运行客户端
     */
    void stop();

    /**
     * 设置协议
     */
    MailClient protocol(String protocol);

    /**
     * 设置邮件处理器
     */
    MailClient mailHandler(MailHandler handler);

    /**
     * 获取客户端参数
     */
    MailProperties getProperties();

    /**
     * 是否正在运行
     */
    boolean isRunning();

    /**
     * 设置参数
     */
    void setProperties(MailProperties properties);

    /**
     * 获取运行时间（毫秒数）
     */
    long getRunningTime();
}

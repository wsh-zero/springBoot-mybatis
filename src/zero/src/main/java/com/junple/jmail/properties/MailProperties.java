package com.junple.jmail.properties;

import java.io.Serializable;
import java.util.Properties;

/**
 * 客户端参数
 * @author chentao
 * @version 2019-04-08
 */
public interface MailProperties extends Serializable {

    /**
     * 转换为java properties
     */
    Properties getProperties(String protocol);

    /**
     * 设置邮件地址
     */
    MailProperties address(String address);

    /**
     * 设置密码
     */
    MailProperties password(String password);

    /**
     * 设置邮件服务地址
     */
    MailProperties host(String host);

    /**
     * 设置端口
     */
    MailProperties port(int port);

    /**
     * 获取邮件服务地址
     */
    String host();

    /**
     * 获取端口
     */
    Integer port();

    /**
     * 获取邮箱地址
     */
    String address();

    /**
     * 获取密码
     */
    String password();
}

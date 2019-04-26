package com.junple.jmail.properties;

import com.junple.jmail.conf.MailConf;
import java.util.Properties;

/**
 * 客户端参数公共抽象类
 * @author chentao
 * @version 2019-04-08
 */
public class SimpleMailProperties implements MailProperties {

    /**
     * 邮箱地址
     */
    protected String address;
    /**
     * 密码
     */
    protected String password;
    /**
     * 邮件服务地址
     */
    protected String host;
    /**
     * 邮件服务端口
     */
    protected Integer port;

    @Override
    public Properties getProperties(String protocol) {

        Properties properties = new Properties();
        switch (protocol) {

            case MailConf.POP3: {
                properties.setProperty(MailConf.PropertieNames.PROTOCOL, MailConf.POP3);
                properties.setProperty(MailConf.PropertieNames.POP3_HOST, this.host);
                properties.setProperty(MailConf.PropertieNames.POP3_PORT, String.valueOf(this.port));
                break;
            }
            case MailConf.IMAP: {
                properties.setProperty(MailConf.PropertieNames.PROTOCOL, MailConf.IMAP);
                properties.setProperty(MailConf.PropertieNames.IMAP_HOST, this.host);
                properties.setProperty(MailConf.PropertieNames.IMAP_PORT, String.valueOf(this.port));
                break;
            }
            default: {
                break;
            }
        }
        return properties;
    }

    @Override
    public MailProperties address(String address) {
        this.address = address;
        return this;
    }

    @Override
    public MailProperties password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public MailProperties host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public MailProperties port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public String host() {
        return this.host;
    }

    @Override
    public Integer port() {
        return this.port;
    }

    @Override
    public String address() {
        return this.address;
    }

    @Override
    public String password() {
        return this.password;
    }
}

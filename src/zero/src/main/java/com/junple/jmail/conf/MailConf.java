package com.junple.jmail.conf;

/**
 * 邮件配置
 * @author chentao
 * @version 2019-04-08
 */
public final class MailConf {

    public static final String POP3 = "pop3";
    public static final String IMAP = "imap";
    public static final Long UPDATE_DURATION = 5000L;

    public static final class PropertieNames {
        /**
         * 协议
         */
        public static final String PROTOCOL = "mail.store.protocol";
        /**
         * pop3端口
         */
        public static final String POP3_PORT = "mail.pop3.port";
        /**
         * pop3服务地址
         */
        public static final String POP3_HOST = "mail.pop3.host";
        /**
         * pop3端口
         */
        public static final String IMAP_PORT = "mail.imap.port";
        /**
         * pop3服务地址
         */
        public static final String IMAP_HOST = "mail.imap.host";
    }
}

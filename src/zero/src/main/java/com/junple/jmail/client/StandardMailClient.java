package com.junple.jmail.client;

import com.junple.jmail.conf.MailConf;
import com.junple.jmail.handler.MailHandler;
import com.junple.jmail.properties.MailProperties;

import javax.mail.*;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 默认的邮件客户端实现
 *
 * @author chentao
 * @version 2019-04-08
 */
public class StandardMailClient implements MailClient {

    /**
     * 邮件客户端参数
     */
    private MailProperties properties;
    /**
     * 协议
     */
    private String protocol = MailConf.IMAP;
    /**
     * 邮件处理器
     */
    private MailHandler handler;
    /**
     * 定时器
     */
    private Timer timer;
    /**
     * 启动时间
     */
    private long startTime;

    @Override
    public MailProperties getProperties() {
        return this.properties;
    }

    @Override
    public boolean isRunning() {
        return timer != null;
    }

    public StandardMailClient(MailProperties properties) {
        this.properties = properties;
    }

    @Override
    public void start() {

        if (timer != null) {
            return;
        }
        Session session = createSession();
        Store store = null;
        if (session != null) {
            store = getStore(session);
        }
        // 开始循环收取邮件
        timerRun(session, store);
        startTime = System.currentTimeMillis();
        System.out.printf("==============邮件客户端已启动（基于%s）==============\n", protocol);
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            System.out.printf("==============邮件客户端已停止==============\n", protocol);
        }
    }

    @Override
    public MailClient protocol(String protocol) {

        this.protocol = protocol;
        return this;
    }

    @Override
    public MailClient mailHandler(MailHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public void setProperties(MailProperties properties) {

        this.properties = properties;
    }

    @Override
    public long getRunningTime() {
        return System.currentTimeMillis() - startTime;
    }

    private Session createSession() {
        Properties prop = properties.getProperties(this.protocol);
        // 创建会话
        Session session = Session.getInstance(prop);
        return session;
    }

    private Store getStore(Session session) {
        Store store = null;
        try {
            store = session.getStore(this.protocol);
            store.connect(properties.address(), properties.password());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return store;
    }

    private Folder openFolderReadWrite(Store store) {
        Folder folder = null;
        try {
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return folder;
    }

    private void timerRun(final Session session, final Store store) {

        this.timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Folder folder = openFolderReadWrite(store);
                if (handler != null) {
                    try {
                        handler.recv(StandardMailClient.this, folder);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, MailConf.UPDATE_DURATION);
    }
}

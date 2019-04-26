package com.junple.jmail.handler;

import com.junple.jmail.client.MailClient;
import javax.mail.Folder;

/**
 * 邮件处理器
 * @author chentao
 * @version 2019-04-08
 */
public interface MailHandler {

    void recv(MailClient context, Folder folder) throws Exception;
}

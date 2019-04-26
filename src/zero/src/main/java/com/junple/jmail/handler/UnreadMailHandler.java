package com.junple.jmail.handler;

import com.junple.jmail.client.MailClient;
import com.junple.jmail.entity.MailObject;

/**
 * 未读取邮件处理器
 *
 * @author chentao
 * @version 2019-04-08
 */
public abstract class UnreadMailHandler extends MailObjectHandler {

    @Override
    public void handleMessage(MailClient context, MailObject mailObject) {
        // 处理未读邮件
        if (mailObject.readed() == false) {
            readMessage(context, mailObject);
            // 设置为已读
            mailObject.setReaded();
        }
    }

    public abstract void readMessage(MailClient context, MailObject mailObject);
}

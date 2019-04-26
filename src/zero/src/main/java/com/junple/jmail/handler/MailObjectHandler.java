package com.junple.jmail.handler;

import com.junple.jmail.client.MailClient;
import com.junple.jmail.entity.DefaultMailObject;
import com.junple.jmail.entity.MailObject;
import javax.mail.Folder;
import javax.mail.Message;

public abstract class MailObjectHandler implements MailHandler {

    @Override
    public void recv(MailClient context, Folder folder) throws Exception {

        Message[] messages = folder.getMessages();
        for (Message message : messages) {
            MailObject mailObject = new DefaultMailObject(message);
            handleMessage(context, mailObject);
        }
    }

    public abstract void handleMessage(MailClient context, MailObject mailObject);
}

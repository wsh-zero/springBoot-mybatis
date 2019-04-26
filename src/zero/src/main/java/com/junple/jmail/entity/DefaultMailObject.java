package com.junple.jmail.entity;

import com.junple.jmail.utils.MailUtils;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 默认的MailObject实现类
 * @author chentao
 * @version 2019-04-08
 */
public class DefaultMailObject implements MailObject {

    private Message message;

    public DefaultMailObject(Message message) {
        this.message = message;
    }

    @Override
    public String getSubject(){
        try {
            return MimeUtility.decodeText(message.getSubject());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getTextContent() {
        return MailUtils.getTextContent(message);
    }

    @Override
    public boolean hasAttach() {
        return MailUtils.hasAttach(message);
    }

    @Override
    public List<MailAttachInfo> getAttachs() {
        return MailUtils.getAttachs(message);
    }

    @Override
    public boolean readed() {
        Flags flags = null;
        try {
            flags = message.getFlags();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // imap有效
        return flags.contains(Flags.Flag.SEEN);
    }

    @Override
    public void setReaded() {
        try {
            message.setFlag(Flags.Flag.SEEN, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSender() {
        return MailUtils.getSender(message);
    }
}

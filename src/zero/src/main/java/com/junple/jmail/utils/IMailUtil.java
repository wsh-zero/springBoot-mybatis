package com.junple.jmail.utils;

import com.junple.jmail.entity.MailAttachInfo;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import java.io.IOException;
import java.util.List;

/**
 * 邮件工具接口
 * @author chentao
 * @version 2019-04-08
 */
public interface IMailUtil {

    /**
     * 判断邮件是否包含附件
     */
    boolean hasAttach(Part message) throws MessagingException, IOException;

    /**
     * 获取邮件文本内容
     */
    String getTextContent(Part message);

    /**
     * 获取附件信息
     */
    List<MailAttachInfo> getAttachs(Part message);

    /**
     * 获取发送者
     */
    String getSender(Part message);
}

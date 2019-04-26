package com.junple.jmail.utils;

import com.junple.jmail.entity.MailAttachInfo;

import javax.mail.MessagingException;
import javax.mail.Part;
import java.io.IOException;
import java.util.List;

/**
 * 邮件静态工具类
 * @author chentao
 * @version 2019-04-08
 */
public final class MailUtils {

    private static IMailUtil util = new MailUtilObject();

    private MailUtils() {

    }

    /**
     * 判断邮件是否包含附件
     */
    public static boolean hasAttach(Part message) {
        try {
            return util.hasAttach(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取邮件文本内容
     */
    public static String getTextContent(Part message) {
        return util.getTextContent(message);
    }

    /**
     * 获取附件信息
     */
    public static List<MailAttachInfo> getAttachs(Part message) {
        return util.getAttachs(message);
    }

    /**
     * 获取发送者
     */
    public static String getSender(Part message) {
        return util.getSender(message);
    }
}

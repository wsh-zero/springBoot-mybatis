package com.junple.jmail.utils;

import com.junple.jmail.entity.DefaultMailAttachInfo;
import com.junple.jmail.entity.MailAttachInfo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件工具类实现类
 *
 * @author chentoa
 * @version 2019-04-08
 */
public class MailUtilObject implements IMailUtil {

    @Override
    public boolean hasAttach(Part part) throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = hasAttach(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("application") != -1) {
                        flag = true;
                    }

                    if (contentType.indexOf("name") != -1) {
                        flag = true;
                    }
                }

                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = hasAttach((Part) part.getContent());
        }
        return flag;
    }

    @Override
    public String getTextContent(Part part) {

        StringBuilder sb = new StringBuilder();
        try {
            getMailTextContent(part, sb);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public List<MailAttachInfo> getAttachs(Part message) {

        List<MailAttachInfo> attachInfos = new ArrayList<>();
        try {
            getAttachments(message, attachInfos);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attachInfos;
    }

    @Override
    public String getSender(Part message) {
        MimeMessage msg = (MimeMessage) message;
        String from = "";
        Address[] froms = new Address[0];
        try {
            froms = msg.getFrom();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        if (froms.length < 1) {
            return null;
        }
        InternetAddress address = (InternetAddress) froms[0];
        return address.getAddress();
    }

    public static void getAttachments(Part part, List<MailAttachInfo> attachs) throws MessagingException, IOException {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();    //复杂体邮件
            //复杂体邮件包含多个邮件体
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                //获得复杂体邮件中其中一个邮件体
                BodyPart bodyPart = multipart.getBodyPart(i);
                //某一个邮件体也有可能是由多个邮件体组成的复杂体
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    InputStream is = bodyPart.getInputStream();
                    DefaultMailAttachInfo attachInfo = new DefaultMailAttachInfo();
                    attachInfo.setAttachFileName(bodyPart.getFileName());
                    attachInfo.setStream(is);
                    attachs.add(attachInfo);
                } else if (bodyPart.isMimeType("multipart/*")) {
                    getAttachments(bodyPart, attachs);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
                        DefaultMailAttachInfo attachInfo = new DefaultMailAttachInfo();
                        attachInfo.setAttachFileName(decodeText(bodyPart.getFileName()));
                        attachInfo.setStream(bodyPart.getInputStream());
                        attachs.add(attachInfo);
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            getAttachments((Part) part.getContent(), attachs);
        }
    }

    private void getMailTextContent(Part part, StringBuilder content) throws MessagingException, IOException {
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part) part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            getMailTextContent(multipart.getBodyPart(partCount - 1), content);
            /*
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart,content);
            }*/
        }
    }

    public static String decodeText(String encodeText) throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }
}

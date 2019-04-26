package com.junple.jmail.entity;

import java.util.List;

/**
 * 邮件实体类
 * @author chentao
 * @version 2019-04-08
 */
public interface MailObject {

    /**
     * 获取主题
     */
    String getSubject();
    /**
     * 获取文本内容
     */
    String getTextContent();
    /**
     * 是否包含附件
     */
    boolean hasAttach();
    /**
     * 获取所有附件信息
     */
    List<MailAttachInfo> getAttachs();
    /**
     * 是否已读
     */
    boolean readed();
    /**
     * 设置为已读
     */
    void setReaded();

    /**
     * 获取发送者
     */
    String getSender();
}

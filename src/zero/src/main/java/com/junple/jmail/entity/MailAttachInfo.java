package com.junple.jmail.entity;

import java.io.InputStream;

/**
 * 邮件附件实体类
 * @author chentoa
 * @version 2019-04-08
 */
public interface MailAttachInfo {

    /**
     * 获取文件名称
     */
    String getAttachFileName();
    /**
     * 获取流
     */
    InputStream getStream();
}

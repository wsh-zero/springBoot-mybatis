package com.junple.jmail.entity;

import java.io.InputStream;

/**
 * 默认的MailAttachInfo实现类
 * @author chentoa
 * @version 2019-04-08
 */
public class DefaultMailAttachInfo implements MailAttachInfo {

    /**
     * 文件名称
     */
    private String attachFileName;
    /**
     * 文件流
     */
    private InputStream stream;

    @Override
    public String getAttachFileName() {
        return attachFileName;
    }

    @Override
    public InputStream getStream() {
        return stream;
    }

    public void setAttachFileName(String attachFileName) {
        this.attachFileName = attachFileName;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }
}

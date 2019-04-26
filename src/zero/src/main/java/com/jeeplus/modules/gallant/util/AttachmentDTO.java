package com.jeeplus.modules.gallant.util;

public class AttachmentDTO {
    // 邮件的MimeMutipart ID
    private int mpid;
    // 附件名称
    private String attName;

    public int getMpid() {
        return mpid;
    }

    public void setMpid(int mpid) {
        this.mpid = mpid;
    }

    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }
}


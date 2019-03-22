package com.wsh.util.mail;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MailBean {
    //邮件主题
    @NotBlank(message = "邮件标题不能为空")
    private String subject;
    //邮件内容
    @NotBlank(message = "邮件内容不能为空")
    private String content;
    //收件人地址
    @NotBlank(message = "收件人地址不能为空")
    private String recipientAddress;
}

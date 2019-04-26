package com.jeeplus.modules.mailclient.core;

import com.junple.jmail.entity.MailObject;

/**
 * 邮件数据解析器
 * @author chentao
 * @version 2019-04-09
 */
public interface MailDataParser {

    /**
     * 解析邮件
     */
    MailData parseMail(MailObject mailObject);
}

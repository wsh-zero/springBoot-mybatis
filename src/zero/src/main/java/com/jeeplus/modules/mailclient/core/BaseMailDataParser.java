package com.jeeplus.modules.mailclient.core;

import com.junple.jmail.entity.MailObject;

public class BaseMailDataParser implements MailDataParser {

    @Override
    public MailData parseMail(MailObject mailObject) {

        mailObject.getTextContent();
        return null;
    }
}

package com.jeeplus.modules.mailclient;

import com.jeeplus.modules.mailclient.mailhandler.ClientUnreadMailHandler;
import com.junple.jmail.client.MailClient;
import com.junple.jmail.client.StandardMailClient;
import com.junple.jmail.entity.MailObject;
import com.junple.jmail.handler.MailHandler;
import com.junple.jmail.handler.UnreadMailHandler;
import com.junple.jmail.properties.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import java.io.*;

/**
 * 邮件客户端组件配置类
 * @author chentao
 * @version 2019-04-09
 */
@Configuration
@Component
public class MailConfiguration {

    @Bean
    public MailProperties mailProperties() throws ClassNotFoundException, IOException {

        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:data/mail/mailproperties.dat");
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        }
        if (file != null && file.exists()) {
            MailProperties properties = (MailProperties) new ObjectInputStream(new FileInputStream(file)).readObject();
            return properties;
        }
        return null;
    }

    @Bean
    public ClientUnreadMailHandler clientUnreadMailHandler() {
        return new ClientUnreadMailHandler();
    }

    @Bean
    public MailClient mailClient() throws IOException, ClassNotFoundException {

        MailClient client = new StandardMailClient(mailProperties())
                .mailHandler(clientUnreadMailHandler());
//        if (client.getProperties() != null) {
//            client.start();
//        }
        return client;
    }
}

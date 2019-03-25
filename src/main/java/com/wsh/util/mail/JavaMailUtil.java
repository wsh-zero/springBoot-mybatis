package com.wsh.util.mail;

import com.wsh.util.ResultUtil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class JavaMailUtil {
    private static final String senderAddress = "254353372@qq.com";
    private static final String senderAccount = "254353372@qq.com";
    private static final String senderPassword = "vhyatuezizibbjif";

    //1、连接邮件服务器的参数配置
    private static final Properties props = new Properties();

    static {
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.qq.com");
    }

    public static ResultUtil sendMail(MailBean mailBean) {
        try {

            //2、创建定义整个应用程序所需的环境信息的 Session 对象
            Session session = Session.getInstance(props);
            //设置调试信息在控制台打印出来
//            session.setDebug(true);

            //3、创建邮件的实例对象
            Message msg = getMimeMessage(session, mailBean);

            //4、根据session对象获取邮件传输对象Transport
            Transport transport = session.getTransport();
            //设置发件人的账户名和密码
            transport.connect(senderAccount, senderPassword);
            //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(msg, msg.getAllRecipients());
            //5、关闭邮件连接
            transport.close();
            return ResultUtil.success("发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failed(1, "发送失败!", e.getMessage());
        }
    }


    /**
     * 获得创建一封邮件的实例对象
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    private static MimeMessage getMimeMessage(Session session, MailBean mailBean) throws Exception {
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailBean.getRecipientAddress()));
//        msg.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(mailBean.getRecipientAddress()));
//        msg.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(mailBean.getRecipientAddress()));
        //4.设置邮件主题
        msg.setSubject(mailBean.getSubject(), "UTF-8");

        //设置邮件正文
        msg.setContent(mailBean.getContent(), "text/html;charset=UTF-8");
        msg.setSentDate(new Date());
        return msg;
    }
}

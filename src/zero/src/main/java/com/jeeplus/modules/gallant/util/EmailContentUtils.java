package com.jeeplus.modules.gallant.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class EmailContentUtils {
    private static Session session=null;

    private static String host = "pop.126.com";

    static {
        Properties props = new Properties();
        //设置邮件接收协议为pop3
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", host);

         session = Session.getInstance(props);

    }

    //private static String host="pop.qq.com";


    //private static Store store=null;

   /* static {
        Properties props=new Properties();
        //设置邮件接收协议为pop3
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", host);
        props.setProperty("mail.pop3.port", "110");

        // SSL安全连接参数
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "true");
        props.setProperty("mail.pop3.socketFactory.port", "995");
        session = Session.getInstance(props);


    }*/


    public static Folder getFolder() throws MessagingException {
       /* Properties props = new Properties();
        //设置邮件接收协议为pop3
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", host);
        props.setProperty("mail.pop3.port", "110");

        // SSL安全连接参数
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "true");
        props.setProperty("mail.pop3.socketFactory.port", "995");
        Session session = Session.getInstance(props);
*/
       Store store = session.getStore("pop3");
        //连接要获取数据的邮箱 主机+用户名+密码
          store.connect(host, "xiongyuan198672@126.com", "xy199771");
       // store.connect(host, "827552899@qq.com", "krdwdeupvdstbbba");
        Folder folder = store.getFolder("inbox");
        //设置邮件可读可写
        folder.open(Folder.READ_WRITE);
     //   store.close();

    return folder;
    }


    public static  Map<String,String> get(int currpage,int sizepage) throws  Exception{
      /*  Properties props=new Properties();
        //设置邮件接收协议为pop3
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", host);
        props.setProperty("mail.pop3.port", "110");

        // SSL安全连接参数
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "true");
        props.setProperty("mail.pop3.socketFactory.port", "995");
        Session session = Session.getInstance(props);*/
            Store store = session.getStore("pop3");
            //连接要获取数据的邮箱 主机+用户名+密码
            store.connect(host, "xiongyuan198672@126.com", "xy199771");
            //store.connect(host, "827552899@qq.com", "krdwdeupvdstbbba");
            Folder folder = store.getFolder("inbox");
            //设置邮件可读可写
            folder.open(Folder.READ_WRITE);
            int j=0;

            Map<String,String> map=new HashMap<>();;
            Message[] messages = folder.getMessages();
            if(currpage>=0 && (currpage+sizepage)<=messages.length)
            for (int i = currpage; i < currpage+sizepage; i++) {
                //解析发件人地址
                // String address = messages[i].getFrom()[0].toString();
                MimeMessage msg = (MimeMessage) messages[i];
                InternetAddress address=(InternetAddress) msg.getFrom()[0];
                String addr=address.getAddress();
                System.out.println("address="+addr);
                //解析邮件主题
                //解决邮件主题乱码的问题
                String subject1 = getSubject(msg); //获得邮件主题
                String subject = "";
                //前面必须判断下是否为null，否则会有异常
                if (subject1 == null || subject1 == "" || "".equals(subject1) || "null".equals(subject1)) {
                    subject = "此邮件没有主题";
                    continue;
                } else {
                    subject = subject1;
                }

               // if("zhaopinmail.com".equals(addr.split("@")[1])) {
                    j++;
                    map.put(i + "", subject1);
                //}
            }
             map.put("count",messages.length+"");

         //   folder.close(true);
         //   store.close();

        return map;

    }



    /**
     * 获得邮件主题
     * @param msg 邮件内容
     * @return 解码后的邮件主题
     */
    public static String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
        try{
            if(msg==null || msg.getSubject()==null)
                return null;
            return MimeUtility.decodeText(msg.getSubject());
        }
        catch(UnsupportedEncodingException e){
            return null;
        }catch(MessagingException e){
            return null;
        }
    }

}

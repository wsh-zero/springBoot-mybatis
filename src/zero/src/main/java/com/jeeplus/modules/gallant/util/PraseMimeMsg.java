package com.jeeplus.modules.gallant.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PraseMimeMsg {

    private MimeMessage mimeMessage = null;
    private StringBuffer bodytext = new StringBuffer();

    public PraseMimeMsg(MimeMessage mimeMessage)
    {
        this.mimeMessage = mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage)
    {
        this.mimeMessage = mimeMessage;
    }

    // 例子： panjia@hstc.com<panjia@hstc.com>
    public String getFrom()throws Exception
    {
        InternetAddress address[] = (InternetAddress[])mimeMessage.getFrom();
        String from = address[0].getAddress();
        if(from == null) from="";
        String personal = address[0].getPersonal();
        if(personal == null) personal="";
        String fromaddr = personal+"<"+from+">";
        return fromaddr;
    }

    public String getSubject()throws MessagingException
    {
        String subject = "";
        try
        {
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
            if(subject == null) subject="";
        }
        catch(Exception exce){ }
        return subject;
    }

    public String getMailAddress(String type)throws Exception
    {
        String mailaddr = "";
        String addtype = type.toUpperCase();
        InternetAddress []address = null;
        if(addtype.equals("TO") || addtype.equals("CC") ||addtype.equals("BCC"))
        {
            if(addtype.equals("TO"))
            {
                address = (InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.TO);
            }
            else if(addtype.equals("CC"))
            {
                address = (InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.CC);
            }
            else
            {
                address = (InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.BCC);
            }
            if(address != null)
            {
                for(int i=0;i<address.length;i++)
                {
                    String email=address[i].getAddress();
                    if(email==null) email="";
                    else
                    {
                        email=MimeUtility.decodeText(email);
                    }
                    String personal=address[i].getPersonal();
                    if(personal==null) personal="";
                    else
                    {
                        personal=MimeUtility.decodeText(personal);
                    }
                    String compositeto=personal+"<"+email+">";
                    mailaddr+=","+compositeto;
                }
                mailaddr=mailaddr.substring(1);
            }
        }
        else
        {
            throw new Exception("Error emailaddr type!");
        }
        return mailaddr;
    }

    public String getBodyText()
    {
        return bodytext.toString();
    }

    public void getMailContent(Part part)throws Exception
    {
        String contenttype = part.getContentType();
        int nameindex = contenttype.indexOf("name");
        boolean conname =false;
        if(nameindex != -1) conname=true;
        System.out.println("CONTENTTYPE: "+contenttype);
        if(part.isMimeType("text/plain") && !conname)
        {
            bodytext.append((String)part.getContent());
        }
        else if(part.isMimeType("text/html") && !conname)
        {
            bodytext.append((String)part.getContent());
        }
        else if(part.isMimeType("multipart/*"))
        {
            Multipart multipart = (Multipart)part.getContent();
            int counts = multipart.getCount();
            for(int i=0;i<counts;i++)
            {
                getMailContent(multipart.getBodyPart(i));
            }
        }
        else if(part.isMimeType("message/rfc822"))
        {
            getMailContent((Part)part.getContent());
        }
        else{}
        //Marking mail as read status.
        if(!mimeMessage.isSet(Flags.Flag.SEEN))
            mimeMessage.setFlag(Flags.Flag.SEEN, true);
    }

    public List<AttachmentDTO> handleMultipart() throws Exception
    {
        List<AttachmentDTO> attachments = new ArrayList<AttachmentDTO>();
        String disposition;
        BodyPart part;
        Multipart mp = (Multipart) mimeMessage.getContent();
        if(mp.getContentType() == "text/plain" || mp.getContentType() == "text/html" ){
            return attachments;
        }
        int mpCount = mp.getCount();
        AttachmentDTO attachmentDTO;
        for (int m = 0; m < mpCount; m++)
        {
            part = mp.getBodyPart(m);
            disposition = part.getDisposition();
            if (disposition != null && disposition.equals(Part.ATTACHMENT))
            {
                String fileName = null;
                fileName = MimeUtility.decodeText(part.getFileName()); //改了
                attachmentDTO = new AttachmentDTO();
                attachmentDTO.setAttName(fileName);
                attachmentDTO.setMpid(m);
                attachments.add(attachmentDTO);
            }
        }
        return attachments;
    }

    public boolean getReplySign()throws MessagingException
    {
        boolean replysign = false;
        String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
        if(needreply != null)
        {
            replysign = true;
        }
        return replysign;
    }

    public double getSize() throws Exception
    {
        float size = mimeMessage.getSize();
        size = size/1024;
        return size;
    }

    public String getMessageId()throws MessagingException
    {
        return mimeMessage.getMessageID();
    }

    public boolean isNew()throws MessagingException
    {
        boolean isnew = false;
        Flags flags = ((Message)mimeMessage).getFlags();
        Flags.Flag []flag = flags.getSystemFlags();
        for(int i=0;i<flag.length;i++)
        {
            if(flag[i] == Flags.Flag.SEEN)
            {
                isnew=true;
                break;
            }
        }
        return isnew;
    }

    public String getSentDate()throws Exception
    {
        Date sentdate = mimeMessage.getSentDate();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(sentdate);
        return dateStr;
    }


}
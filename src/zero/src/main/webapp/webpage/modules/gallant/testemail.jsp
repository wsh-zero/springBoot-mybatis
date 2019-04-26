<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/13 0013
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.jeeplus.modules.gallant.util.AttachmentDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.mail.internet.MimeMessage" %>
<%@ page import="com.jeeplus.modules.gallant.util.PraseMimeMsg" %>
<%@ page import="java.util.Properties" %>
<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.Part" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>简历管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">求职者简历列表</h3>
        </div>
        <a href="${ctx}/gallant/gallantapplication/reademail/0">返回</a>
        <div class="panel-body">
<%
   // Users user = (Users) request.getSession().getAttribute(Const.USERNAME);
   Properties props = new Properties();
		String host = "pop.126.com";
		//设置邮件接收协议为pop3
		props.setProperty("mail.store.protocol", "pop3");
		props.setProperty("mail.pop3.host", host);

		Session session1 = Session.getInstance(props);
		Store store = session1.getStore("pop3");
		//连接要获取数据的邮箱 主机+用户名+密码
		store.connect(host, "15210824526@126.com", "xiongyuan198672");
		Folder folder = store.getFolder("inbox");
		//设置邮件可读可写
		folder.open(Folder.READ_WRITE);
    // 2、读取邮件夹
   // Folder folder = (Folder)session.getAttribute("folder");
    // 获取邮件夹中第i封邮件信息
    //Integer msgnum = Integer.parseInt(request.getParameter("msgnum"));
    Integer msgnum = Integer.parseInt(request.getAttribute("msgnum").toString());
    //Integer msgnum=600;
    String subject = null;
    String from = null;
    String to = null;
    Object content = null;
    String sendDate = null;
    List<AttachmentDTO> attachmentDTOList = null;
    try {
        Message[] messages = folder.getMessages();
        MimeMessage message = (MimeMessage) messages[msgnum];
        PraseMimeMsg pmm = new PraseMimeMsg(message);
        subject = pmm.getSubject();
        from = pmm.getFrom();
      //  to = pmm.getMailAddress("to");
        sendDate = pmm.getSentDate();
        pmm.getMailContent((Part) message);
        content = pmm.getBodyText();
        attachmentDTOList = pmm.handleMultipart();

    } catch (MessagingException e) {
        e.printStackTrace();
    }
%>



<center><h1><%=subject%></h1></center>
<p>  正文：<%=content%></p> <br/>
<%
    for(int i  = 0; i < attachmentDTOList.size(); i++) {
%>
附件：<a href="${ctx}/gallant/gallantapplication/getEmailDetail?msgnum=${}&&bodynum=${}&&filename=<%=((AttachmentDTO)attachmentDTOList.get(i)).getAttName()%>"> <%=((AttachmentDTO)attachmentDTOList.get(i)).getAttName()%></a>
<%
    }
%>

<div><span>发件人:<%=from%></span></div>
<div><span>发送时间：<%=sendDate%></span></div>
<hr/>

</body>
</html>

<%@ page import="com.jeeplus.modules.gallant.util.AttachmentDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
        <div class="panel-body">

            <center><h1>${subject}</h1></center>
            <p>  正文：${content}</p> <br/>
                <c:forEach items="${attachmentDTOList}" var="dto">
            附件：<a href="${ctx}/gallant/gallantapplication/getEmailDetail?msgnum=${msgnum}&&bodynum=${dto.mpid}&&filename=${dto.attName}">${dto.attName}</a>
            </c:forEach>
            <div><span>发件人:${from}</span></div>
            <div><span>发送时间：${sendDate}</span></div>
            <hr/>


</body>
</html>

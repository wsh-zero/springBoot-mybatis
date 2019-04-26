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
<table>
    <tr>
        <td>序号</td>
        <td>标题</td>
    </tr>
    <c:forEach items="${map}" var="c" varStatus="vs">
    <tr>
        <td>${vs.count}</td>
        <td><a href="${ctx}/gallant/gallantapplication/reademail/${c.key}">${c.value}</a></td>
    </tr>
    </c:forEach>
</table>
                        <table>
                            <tr>
                                <td><a href="${ctx}/gallant/gallantapplication/getemailcontent?currpage=1">首页</a></td>
                                <c:if test="${pb.currpage!=1}">
                                    <td><a href="${ctx}/gallant/gallantapplication/getemailcontent?currpage=${pb.currpage-1}">上一页</a></td>
                                </c:if>
                                <c:if test="${pb.currpage!=pb.countpage}">
                                    <td><a href="${ctx}/gallant/gallantapplication/getemailcontent?currpage=${pb.currpage+1}">下一页</a></td>
                                </c:if>
                                <td><a href="${ctx}/gallant/gallantapplication/getemailcontent?currpage=${pb.countpage}">末页</a></td>
                            </tr>

                        </table>
</body>
</html>

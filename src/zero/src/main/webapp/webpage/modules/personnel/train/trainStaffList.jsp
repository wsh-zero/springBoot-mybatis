<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>培训管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="trainStaff.js" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">
                <a class="panelButton" href="${ctx}/personnel/train/train"><i class="ti-angle-left"></i> 返回</a>
            </h3>
        </div>
        <div class="panel-body">
            <div id="toolbar">
                    <%--<shiro:hasPermission name="personnel:train:train:staffedit">--%>
                        <button id="edit" class="btn btn-success" disabled onclick="edit()">
                            <i class="glyphicon glyphicon-edit"></i> 修改
                        </button>
                    <%--</shiro:hasPermission>--%>
                    <%--<shiro:hasPermission name="personnel:train:train:staffview">--%>
                        <button id="view" class="btn btn-default" disabled onclick="view()">
                            <i class="fa fa-search-plus"></i> 查看
                        </button>
                    <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="personnel:train:train:list">--%>
                        <%--<button id="back" class="btn" disabled onclick="back()">--%>
                            <%--<i class="fa fa-search-plus"></i> 返回--%>
                        <%--</button>--%>
                        <%--</shiro:hasPermission>--%>
                </div>

            <%--</c:if>--%>
            <!-- 表格 -->
            <table id="trainTable" data-toolbar="#toolbar"></table>


                <!-- context menu -->
                <ul id="context-menu" class="dropdown-menu">
                    <%--<shiro:hasPermission name="personnel:train:train:staffview">--%>
                        <li data-item="view"><a>查看</a></li>
                    <%--</shiro:hasPermission>--%>
                    <%--<shiro:hasPermission name="personnel:train:train:staffedit">--%>
                        <li data-item="edit"><a>编辑</a></li>
                    <%--</shiro:hasPermission>--%>
                    <li data-item="action1"><a>取消</a></li>
                </ul>

        </div>
    </div>
</div>
</body>
</html>
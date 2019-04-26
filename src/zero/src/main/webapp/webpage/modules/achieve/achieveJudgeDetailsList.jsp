<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效评定详情管理</title>
    <meta name="decorator" content="ani"/>
    <%@include file="achieveJudgeDetailsList.js" %>
</head>
<body>
<script type="text/javascript">
    function achieve_judge_back() {
        window.location.href = '${ctx}/achieve/achieveConfig?tab=3'
    }

    $(document).keydown(function (event) {
        if (event.keyCode == '27') {
            achieve_judge_back()
        }
    });
</script>

<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">绩效评定详情列表 <a class="panelButton pull-right" href="javascript:void(0)"
                                                onclick="achieve_judge_back()"><i
                    class="ti-angle-left"></i> 返回</a></h3>
        </div>
        <div class="panel-body">

            <%--<!-- 工具栏 -->--%>
            <%--<div class="row">--%>
            <%--<div class="col-sm-12">--%>
            <%--<div class="pull-left treetable-bar">--%>
            <%--<shiro:hasPermission name="achieve:achieveJudgeDetails:add">--%>
            <%--<a id="add" class="btn btn-primary"--%>
            <%--onclick="jp.openSaveDialog('新建绩效评定详情', '${ctx}/achieve/achieveJudgeDetails/form','800px', '500px',judge_details_refreshNode)"><i--%>
            <%--class="glyphicon glyphicon-plus"></i> 新建</a><!-- 增加按钮 -->--%>
            <%--</shiro:hasPermission>--%>
            <%--<button class="btn btn-default" data-toggle="tooltip" data-placement="left" onclick="judge_details_refresh()"--%>
            <%--title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新--%>
            <%--</button>--%>

            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <table id="achieveJudgeDetailsTreeTable" class="table table-hover">
                <thead>
                <tr>
                    <th>考核指标</th>
                    <th>A级</th>
                    <th>B级</th>
                    <th>C级</th>
                    <th>D级</th>
                    <%--<th>操作</th>--%>
                </tr>
                </thead>
                <tbody id="achieveJudgeDetailsTreeTableList"></tbody>
            </table>
            <br/>
        </div>
    </div>
</div>
</body>
</html>
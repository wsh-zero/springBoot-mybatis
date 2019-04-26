<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效考核规则详情管理</title>
    <meta name="decorator" content="ani"/>
    <%@include file="achieveRuleDetailsList.js" %>
    <script type="text/javascript">
        var achieveRuleId = '${achieveRuleDetails.achieveRuleId}';
        var totalScore1 = '${achieveRuleDetails.totalScore1}';
        var totalScore3 = '${achieveRuleDetails.totalScore3}';
        var params = "?achieveRuleId=" + achieveRuleId + "&totalScore1=" + totalScore1
            + "&totalScore3=" + totalScore3;
        $(document).ready(function () {
        });


    </script>
</head>
<body class="bg-white">
<form:form modelAttribute="achieveRuleDetails" class="form-horizontal">
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>任务类型：</label></td>
            <td class="width-35">
                <form:select path="type" class="form-control required" id="onchange_type">
                    <form:option value="1" label="">常规任务</form:option>
                    <form:option value="2" label="">新增任务</form:option>
                    <form:option value="3" label="">临时任务</form:option>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">总分值:</label></td>
            <td class="width-35">
                新增/常规任务: ${achieveRuleDetails.totalScore1} 分<br/>
                临时任务: ${achieveRuleDetails.totalScore3} 分
            </td>
        </tr>
        <tr>

            <table id="achieveRuleDetailsTreeTable" class="table table-hover">
                <thead>
                <tr>
                    <th>任务类型</th>
                    <th>任务内容</th>
                    <th>计划完成量</th>
                    <th>计划完成时间</th>
                    <th>分值</th>
                    <th width="180px">操作</th>
                </tr>
                </thead>
                <tbody id="achieveRuleDetailsTreeTableList">
                <!-- 工具栏 -->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="pull-left treetable-bar">
                            <shiro:hasPermission name="achieve:achieveRuleDetails:add">
                                <a id="add" class="btn btn-primary"
                                   onclick="jp.openSaveDialog('新建绩效考核规则详情', '${ctx}/achieve/achieveRuleDetails/form2'+params
                                           +'&type='+$('#onchange_type option:selected').val(),'800px', '500px',refreshNode)"><i
                                        class="glyphicon glyphicon-plus"></i> 新建</a><!-- 增加按钮 -->
                            </shiro:hasPermission>
                                <%--<button class="btn btn-default" data-toggle="tooltip" data-placement="left"--%>
                                <%--onclick="refresh()"--%>
                                <%--title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新--%>
                                <%--</button>--%>
                        </div>
                    </div>
                </div>
                </tbody>
            </table>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
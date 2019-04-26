<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效考核规则详情管理</title>
    <meta name="decorator" content="ani"/>
    <%@include file="achieveRuleDetailsList2.js" %>
</head>
<body>

<script type="text/javascript">
    function achieve_rule_back() {
        window.location.href = '${ctx}/achieve/achieveConfig?tab=4'
    }

    $(document).keydown(function (event) {
        if (event.keyCode == '27') {
            achieve_rule_back()
        }
    });
</script>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">绩效评定详情列表 <a class="panelButton pull-right" href="javascript:void(0)"
                                                onclick="achieve_rule_back()"><i
                    class="ti-angle-left"></i> 返回</a></h3>
        </div>

        <div class="panel-body">
            <pre>评分表名称:${achieveRuleDetails.ruleName}     评分表编号:${achieveRuleDetails.ruleNumber}</pre>
            <table id="achieveRuleDetailsTreeTable" class="table table-hover">
                <thead>
                <tr>
                    <th>任务类型</th>
                    <th>任务内容</th>
                    <th>计划完成量</th>
                    <th>计划完成时间</th>
                    <th>分值</th>
                    <th>A</th>
                    <th>B</th>
                    <th>C</th>
                    <th>D</th>
                </tr>

                </thead>
                <tbody id="achieveRuleDetailsTreeTableList"></tbody>
            </table>
            <br/>
        </div>
    </div>
</div>
</body>
</html>
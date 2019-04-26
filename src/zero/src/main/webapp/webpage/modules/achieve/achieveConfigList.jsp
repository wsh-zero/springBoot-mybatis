<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效配置管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="achieveConfigList.js" %>
    <%@include file="achieveObjList.js" %>
    <%@include file="achieveJudgeList.js" %>
    <%@include file="achieveRuleList.js" %>
</head>
<body>
<script type="text/javascript">
    $(function () {
        var tab = '${achieveConfig.tab}';
        var $tab = $("#tab_" + tab);
        switch (tab) {
            case "1":
                $tab.addClass("active");
                $("#evaluateConfig").addClass("in active");
                break;
            case "2":
                $tab.addClass("active");
                $("#objectConfig").addClass("in active");
                break;
            case "3":
                $tab.addClass("active");
                $("#evaluateTableConfig").addClass("in active");
                break;
            case "4":
                $tab.addClass("active");
                $("#addRule").addClass("in active");
                break;
            default:
                $tab.addClass("active");
                $("#evaluateConfig").addClass("in active");
        }
    })
</script>
<ul id="myTab" class="nav nav-tabs">
    <li id="tab_1"><a href="#evaluateConfig" data-toggle="tab">评定配置 </a></li>
    <li id="tab_2"><a href="#objectConfig" data-toggle="tab">对象配置</a></li>
    <li id="tab_3"><a href="#evaluateTableConfig" data-toggle="tab">评定表配置</a></li>
    <li id="tab_4"><a href="#addRule" data-toggle="tab">添加规则</a></li>

</ul>
<div id="myTabContent" class="tab-content">
    <div class="tab-pane fade" id="evaluateConfig">

        <div class="wrapper wrapper-content">
            <div class="panel panel-primary">

                <div class="panel-heading">
                    <h3 class="panel-title">绩效配置列表</h3>
                </div>
                <div class="panel-body">

                    <!-- 工具栏 -->
                    <div id="toolbar">
                        <shiro:hasPermission name="achieve:achieveConfig:add">
                            <button id="add" class="btn btn-primary" onclick="add()">
                                <i class="glyphicon glyphicon-plus"></i> 新建
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveConfig:edit">
                            <button id="edit" class="btn btn-success" disabled onclick="edit()">
                                <i class="glyphicon glyphicon-edit"></i> 修改
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveConfig:del">
                            <button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
                                <i class="glyphicon glyphicon-remove"></i> 删除
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveStandard:list">
                            <button id="view" class="btn btn-default" disabled onclick="toStandard()">
                                <i class="fa fa-search-plus"></i> 查看
                            </button>
                        </shiro:hasPermission>
                    </div>

                    <!-- 表格 -->
                    <table id="achieveConfigTable" data-toolbar="#toolbar"></table>

                    <!-- context menu -->
                    <ul id="context-menu" class="dropdown-menu">
                        <shiro:hasPermission name="achieve:achieveConfig:view">
                            <li data-item="view"><a>查看</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveConfig:edit">
                            <li data-item="edit"><a>编辑</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveConfig:del">
                            <li data-item="delete"><a>删除</a></li>
                        </shiro:hasPermission>
                        <li data-item="action1"><a>取消</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="tab-pane fade" id="objectConfig">

        <div class="wrapper wrapper-content">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">绩效对象配置列表</h3>
                </div>
                <div class="panel-body">

                    <!-- 工具栏 -->
                    <div id="obj-toolbar">
                        <shiro:hasPermission name="achieve:achieveObj:add">
                            <button id="obj_add" class="btn btn-primary" onclick="obj_add()">
                                <i class="glyphicon glyphicon-plus"></i> 新建
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveObj:edit">
                            <button id="obj_edit" class="btn btn-success" disabled onclick="obj_edit()">
                                <i class="glyphicon glyphicon-edit"></i> 修改
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveObj:del">
                            <button id="obj_remove" class="btn btn-danger" disabled onclick="obj_deleteAll()">
                                <i class="glyphicon glyphicon-remove"></i> 删除
                            </button>
                        </shiro:hasPermission>
                    </div>

                    <!-- 表格 -->
                    <table id="achieveObjTable" data-toolbar="#obj-toolbar"></table>

                    <!-- context menu -->
                    <ul id="obj-context-menu" class="dropdown-menu">
                        <shiro:hasPermission name="achieve:achieveObj:edit">
                            <li data-item="obj_edit"><a>编辑</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveObj:del">
                            <li data-item="obj_delete"><a>删除</a></li>
                        </shiro:hasPermission>
                        <li data-item="action1"><a>取消</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="tab-pane fade" id="evaluateTableConfig">

        <div class="wrapper wrapper-content">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">绩效评定表配置列表</h3>
                </div>
                <div class="panel-body">

                    <!-- 工具栏 -->
                    <div id="judge_toolbar">
                        <shiro:hasPermission name="achieve:achieveJudge:add">
                            <button id="judge_add" class="btn btn-primary" onclick="judge_add()">
                                <i class="glyphicon glyphicon-plus"></i> 新建
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveJudge:edit">
                            <button id="judge_edit" class="btn btn-success" disabled onclick="judge_edit()">
                                <i class="glyphicon glyphicon-edit"></i> 修改
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveJudge:del">
                            <button id="judge_remove" class="btn btn-danger" disabled onclick="judge_deleteAll()">
                                <i class="glyphicon glyphicon-remove"></i> 删除
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveJudge:view">
                            <button id="judge_view" class="btn btn-default" disabled onclick="toJudgeDetails()">
                                <i class="fa fa-search-plus"></i> 查看
                            </button>
                        </shiro:hasPermission>
                    </div>

                    <!-- 表格 -->
                    <table id="achieveJudgeTable" data-toolbar="#judge_toolbar"></table>

                    <!-- context menu -->
                    <ul id="judge-context-menu" class="dropdown-menu">
                        <shiro:hasPermission name="achieve:achieveJudge:view">
                            <li data-item="judge_view"><a>查看</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveJudge:edit">
                            <li data-item="judge_edit"><a>编辑</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveJudge:del">
                            <li data-item="judge_delete"><a>删除</a></li>
                        </shiro:hasPermission>
                        <li data-item="action1"><a>取消</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="tab-pane fade" id="addRule">
        <div class="wrapper wrapper-content">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">绩效考核规则列表</h3>
                </div>
                <div class="panel-body">


                    <!-- 工具栏 -->
                    <div id="achieve_rule_toolbar">
                        <shiro:hasPermission name="achieve:achieveRule:add">
                            <button id="achieve_rule_add" class="btn btn-primary" onclick="achieve_rule_add()">
                                <i class="glyphicon glyphicon-plus"></i> 新建
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveRule:edit">
                            <button id="achieve_rule_edit" class="btn btn-success" disabled
                                    onclick="achieve_rule_edit()">
                                <i class="glyphicon glyphicon-edit"></i> 修改
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveRule:del">
                            <button id="achieve_rule_remove" class="btn btn-danger" disabled
                                    onclick="achieve_rule_deleteAll()">
                                <i class="glyphicon glyphicon-remove"></i> 删除
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveRule:add">
                            <button id="achieve_rule_add_content" class="btn btn-info" disabled
                                    onclick="achieve_rule_add_content()">
                                <i class="glyphicon glyphicon-plus"></i> 编辑考核内容
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="achieve:achieveRule:view">
                            <button id="achieve_rule_view" class="btn btn-default" disabled
                                    onclick="toRuleDetails()">
                                <i class="fa fa-search-plus"></i> 查看
                            </button>
                        </shiro:hasPermission>
                    </div>

                    <!-- 表格 -->
                    <table id="achieveRuleTable" data-toolbar="#achieve_rule_toolbar"></table>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
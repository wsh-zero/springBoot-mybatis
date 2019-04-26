<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效评定表配置管理</title>
    <meta name="decorator" content="ani"/>
    <%@include file="achieveJudgeDetailsList.js" %>
    <script type="text/javascript">

        $(document).ready(function () {

        });

        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if (!isValidate) {
                return false;
            } else {
                jp.loading();
                jp.post("${ctx}/achieve/achieveJudge/save", $('#inputForm').serialize(), function (data) {
                    if (data.success) {
                        jp.getParent().judge_refresh();
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                        jp.success(data.msg)

                    } else {
                        jp.error(data.msg);
                    }
                })
            }

        }
    </script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="achieveJudge" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="temporaryId"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>评定表名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" class="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>考核标准：</label></td>
            <td class="width-35">

                <form:select path="achieveConfigId" id="rank_id" class="form-control required">
                    <form:option value=""></form:option>
                    <c:forEach items="${achieveConfig}" var="o">
                        <form:option value="${o.key}" label="">${o.value}</form:option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <table id="achieveJudgeDetailsTreeTable" class="table table-hover">
                <thead>
                <tr>
                    <th>考核指标</th>
                    <th>A级</th>
                    <th>B级</th>
                    <th>C级</th>
                    <th>D级</th>
                    <th width="180px">操作</th>
                </tr>
                </thead>
                <tbody id="achieveJudgeDetailsTreeTableList">

                <!-- 工具栏 -->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="pull-left treetable-bar">
                            <shiro:hasPermission name="achieve:achieveJudgeDetails:add">
                                <a id="add" class="btn btn-primary"
                                   onclick="jp.openSaveDialog('新建绩效评定详情', '${ctx}/achieve/achieveJudgeDetails/form?achieveJudgeId='
                                           +'${achieveJudge.id}'+'&temporaryId='+'${achieveJudge.temporaryId}','800px', '500px',judge_details_refreshNode)">
                                    <i class="glyphicon glyphicon-plus"></i> 新建</a><!-- 增加按钮 -->
                            </shiro:hasPermission>

                                <%--<button class="btn btn-default" data-toggle="tooltip" data-placement="left"--%>
                                <%--onclick="judge_details_refresh()"--%>
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
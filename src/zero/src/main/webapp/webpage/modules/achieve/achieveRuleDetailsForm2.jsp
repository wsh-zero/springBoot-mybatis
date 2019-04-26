<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效考核规则详情管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">

        $(document).ready(function () {
            $('#planSuccTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
        });

        function save(fun) {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if (!isValidate) {
                return false;
            } else {
                jp.loading();
                jp.post("${ctx}/achieve/achieveRuleDetails/save", $('#inputForm').serialize(), function (data) {
                    if (data.success) {
                        fun(data);
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
<form:form id="inputForm" modelAttribute="achieveRuleDetails" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <input name="achieveRuleId" type="hidden" value="${achieveRuleDetails.achieveRuleId}"/>
    <input name="type" type="hidden" value="${achieveRuleDetails.type}"/>
    <input name="totalScore1" type="hidden" value="${achieveRuleDetails.totalScore1}"/>
    <input name="totalScore3" type="hidden" value="${achieveRuleDetails.totalScore3}"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">任务内容：</label></td>
            <td class="width-35">
                <form:textarea path="taskContent" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">计划完成量：</label></td>
            <td class="width-35">
                <form:textarea path="planSuccAmount" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr style="display: none">
            <td class="width-15 active"><label class="pull-right">父级编号：</label></td>
            <td class="width-35">
                <sys:treeselect id="parent" name="parent.id" value="${achieveRuleDetails.parent.id}"
                                labelName="parent.name" labelValue="${achieveRuleDetails.parent.name}"
                                title="父级编号" url="/achieve/achieveRuleDetails/treeData" extId="${achieveRuleDetails.id}"
                                cssClass="form-control " allowClear="true"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">计划完成时间：</label></td>
            <td class="width-35">
                <div class='input-group form_datetime' id='planSuccTime'>
                    <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
					</span>
                    <input type='text' name="planSuccTime" class="form-control "
                           value="<fmt:formatDate value="${achieveRuleDetails.planSuccTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>

                </div>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
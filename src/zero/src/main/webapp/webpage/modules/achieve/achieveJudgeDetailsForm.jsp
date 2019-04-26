<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效评定详情管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">

        $(document).ready(function () {

        });

        function save(fun) {
            var isValidate = jp.validateForm('#judge_details_inputForm');//校验表单
            if (!isValidate) {
                return false;
            } else {
                jp.loading();
                jp.post("${ctx}/achieve/achieveJudgeDetails/save", $('#judge_details_inputForm').serialize(), function (data) {
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
<form:form id="judge_details_inputForm" modelAttribute="achieveJudgeDetails" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="achieveJudgeId"/>
    <form:hidden path="temporaryId"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>考核指标：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" class="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right">父级编号：</label></td>
            <td class="width-35">
                <sys:treeselect id="parent" name="parent.id" value="${achieveJudgeDetails.parent.id}"
                                labelName="parent.name" labelValue="${achieveJudgeDetails.parent.name}"
                                title="父级编号" url="/achieve/achieveJudgeDetails/treeData?achieveJudgeId=${achieveJudgeDetails.achieveJudgeId}"
                                extId="${achieveJudgeDetails.id}" cssClass="form-control " allowClear="true"/>
            </td>
        <tr>
            <td class="width-15 active"><label class="pull-right">A级：</label></td>
            <td class="width-35">
                <form:input path="achieveA" htmlEscape="false" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">B级：</label></td>
            <td class="width-35">
                <form:input path="achieveB" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">C级：</label></td>
            <td class="width-35">
                <form:input path="achieveC" htmlEscape="false" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">D级：</label></td>
            <td class="width-35">
                <form:input path="achieveD" htmlEscape="false" class="form-control "/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
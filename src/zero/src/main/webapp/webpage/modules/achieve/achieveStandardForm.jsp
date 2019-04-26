<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效评定标准管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">

        $(document).ready(function () {

        });

        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if (!isValidate) {
                return false;
            } else {
                jp.loading();
                jp.post("${ctx}/achieve/achieveStandard/save", $('#inputForm').serialize(), function (data) {
                    if (data.success) {
                        jp.getParent().refresh();
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
<form:form id="inputForm" modelAttribute="achieveStandard" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="achieveConfigId"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red">*</span>考核标准：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red">*</span>分值：</label></td>
            <td class="width-35">
                <form:input path="score" htmlEscape="false" class="form-control required digits " max="100" min="0"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效对象配置管理</title>
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
                jp.post("${ctx}/achieve/achieveObj/save", $('#inputForm').serialize(), function (data) {
                    if (data.success) {
                        jp.getParent().obj_refresh();
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                        jp.success(data.msg)

                    } else {
                        jp.error(data.msg);
                    }
                })
            }

        }

        function getRankDept() {
            var initRankId = '${achieveObj.rankId}';
            var deptIdList = '${achieveObj.deptIdList}';

            var rankId = $('#rank_id option:selected').val();
            jp.post("${ctx}/sys/office/get/rank/dept", {rankId: rankId}, function (data) {
                var html = "";
                if (data.length > 0) {
                    for (var item in data) {

                        if (initRankId === rankId && deptIdList.indexOf(data[item].key) > 0) {
                            html += '<label><input name="deptIdList" type="checkbox" value="' + data[item].key + '" checked="checked" class="i-checks required">' + data[item].value
                        } else {
                            html += '<label><input name="deptIdList" type="checkbox" value="' + data[item].key + '" class=" i-checks required">' + data[item].value + '</label>'
                        }
                    }
                    html += '<label class="error" for="deptIdList"></label>'
                    $("#from_dept_td").html(html);

                    $('input[type=radio],input[type=checkbox]').iCheck({
                        labelHover: false,
                        cursor: true,
                        checkboxClass: 'icheckbox_square-blue',
                        radioClass: 'iradio_square-blue',
                        increaseArea: '20%'
                    });
                } else {
                    html += '<label><input name="deptIdList" type="hidden" value="" class=" i-checks required"></label>';
                    $("#from_dept_td").html(html);
                }

            })
        }
    </script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="achieveObj" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>分组名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>选择职称：</label></td>
            <td class="width-35">

                <form:select path="rankId" id="rank_id" class="form-control required" onchange="getRankDept()">
                    <form:option value=""></form:option>
                    <c:forEach items="${rank}" var="o">
                        <form:option value="${o.key}" label="">${o.value}</form:option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="active"><label class="pull-right"><span style="color: red; ">*</span>选择部门:</label></td>
            <td id="from_dept_td">
                <form:checkboxes path="deptIdList" items="${dept}" itemLabel="value" itemValue="key"
                                 htmlEscape="false" cssClass="i-checks required"/>
                <label class="error" for="deptIdList"></label>

            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
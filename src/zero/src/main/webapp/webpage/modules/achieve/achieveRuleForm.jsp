<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>绩效考核规则管理</title>
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
                jp.post("${ctx}/achieve/achieveRule/save", $('#inputForm').serialize(), function (data) {
                    if (data.success) {
                        jp.getParent().achieve_rule_refresh();
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                        jp.success(data.msg)

                    } else {
                        jp.error(data.msg);
                    }
                })
            }
        }

        function setTotalScore_() {
            var achieveJudgeId = $('#achieve_judge_id option:selected').val();
            if (achieveJudgeId === "0") {
            }
        }
    </script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="achieveRule" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>评分表名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" class="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>评分表编号：</label></td>
            <td class="width-35">
                <form:input path="number" htmlEscape="false" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>被考核人：</label></td>
            <td class="width-35">

                <form:select path="achieveObjId" class="form-control required">
                    <form:option value=""></form:option>
                    <c:forEach items="${achieveObj}" var="o">
                        <form:option value="${o.key}" label="">${o.value}</form:option>
                    </c:forEach>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>考核人：</label></td>
            <td class="width-35">
                <form:select path="assessor" class="form-control required">
                    <form:option value=""></form:option>
                    <form:option value="1" label="">直接上级</form:option>
                    <form:option value="2" label="">直接下级</form:option>
                    <form:option value="3" label="">各部门同级</form:option>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>考核周期：</label></td>
            <td class="width-35">
                <form:select path="assessorCycle" class="form-control required">
                    <form:option value=""></form:option>
                    <form:option value="1" label="">月</form:option>
                    <form:option value="2" label="">季</form:option>
                    <form:option value="3" label="">年</form:option>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>状态：</label></td>
            <td class="width-35">
                <form:radiobuttons path="state" items="${fns:getDictList('enable_disabled')}" itemLabel="label"
                                   itemValue="value" htmlEscape="false" cssClass="i-checks required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>评定表：</label></td>
            <td class="width-35">
                <form:select path="achieveJudgeId" id="achieve_judge_id" class="form-control required"
                             onchange="setTotalScore_()">
                    <form:option value=""></form:option>
                    <c:forEach items="${achieveJudge}" var="o">
                        <form:option value="${o.key}" label="">${o.value}</form:option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>非任务维度总分值：</label>
            </td>
            <td class="width-35">
                <form:input path="totalScore0" htmlEscape="false" class="form-control number required"/>
            </td>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>常规任务总分值：</label>
            </td>
            <td class="width-35">
                <form:input path="totalScore1" htmlEscape="false" class="form-control number required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>临时任务总分值：</label>
            </td>
            <td class="width-35">
                <form:input path="totalScore3" htmlEscape="false" class="form-control number required"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
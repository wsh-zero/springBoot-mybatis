<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>员工合同管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">

        $(document).ready(function () {

            $('#contractStart').datetimepicker({
                format: "YYYY-MM-DD"
            });
            $('#contractEnd').datetimepicker({
                format: "YYYY-MM-DD"
            });
            $('#secretStart').datetimepicker({
                format: "YYYY-MM-DD"
            });
            $('#secretEnd').datetimepicker({
                format: "YYYY-MM-DD"
            });
        });
        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if (!isValidate) {
                return false;
            } else {
                jp.loading();
                jp.post("${ctx}/personnel/manager/contract/save", $('#inputForm').serialize(), function (data) {
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
<form:form id="inputForm" modelAttribute="contract" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <%--<c:if test="${ mode=='edit'|| mode=='view'}">--%>
            <tr>
                <td class="width-15 active"><label class="pull-right">合同编号：</label></td>
                <td class="width-35">
                    <form:input path="contractCode" htmlEscape="false"  class="form-control"/>
                </td>
                <td class="width-15 active"><label class="pull-right">保密协议编号：</label></td>
                <td class="width-35">
                    <form:input path="secretCode" htmlEscape="false"  class="form-control "/>
                </td>

            </tr>
        <%--</c:if>--%>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
            <td class="width-35">
                <sys:gridselect url="${ctx}/personnel/manager/staff/data" id="staffName" name="staffName.id"
                                value="${contract.staffName.id}" labelName="staffName.name"
                                labelValue="${contract.staffName.name}"
                                title="选择姓名" cssClass="form-control required" fieldLabels="部门|名字"
                                fieldKeys="depart.name|name" searchLabels="部门|名字"
                                searchKeys="depart.name|name"></sys:gridselect>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同类型：</label></td>
            <td class="width-35">
                <form:select path="contractType" id="getType"  class="form-control required">
                    <form:option value=""></form:option>
                    <c:forEach items="${type}" var="o">
                        <form:option value="${o.id}" label="">${o.name}</form:option>
                    </c:forEach>
                    <%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                </form:select>
            </td>

        </tr>
        <tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同开始日期：</label></td>
            <td class="width-35">
                <div class='input-group form_datetime' id='contractStart'>
                    <input type='text' name="contractStart" class="form-control required"
                           value="<fmt:formatDate value="${contract.contractStart}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
                </div>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>协议起始日：</label></td>
            <td class="width-35">
                <div class='input-group form_datetime' id='secretStart'>
                    <input type='text' name="secretStart" class="form-control required"
                           value="<fmt:formatDate value="${contract.secretStart}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
                </div>
            </td>
        </tr>
        <td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同终止日期：</label></td>
        <td class="width-35">
            <div class='input-group form_datetime' id='contractEnd'>
                <input type='text' name="contractEnd" class="form-control required"
                       value="<fmt:formatDate value="${contract.contractEnd}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
            </div>
        </td>

        <td class="width-15 active"><label class="pull-right"><font color="red">*</font>协议终止日：</label></td>
        <td class="width-35">
            <div class='input-group form_datetime' id='secretEnd'>
                <input type='text' name="secretEnd" class="form-control required"
                       value="<fmt:formatDate value="${contract.secretEnd}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
            </div>
        </td>
        </tr>
            <%--<tr>--%>

            <%--<td class="width-15 active"><label class="pull-right">说明：</label></td>--%>
            <%--<td class="width-35">--%>
            <%--<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>--%>
            <%--</td>--%>
            <%--</tr>--%>
        <tr>
            <td class="width-15 active"><label class="pull-right">签订次数：</label></td>
            <td class="width-35">
                <form:input path="signNumber" htmlEscape="false"
                            onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                            class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">附件：</label></td>
            <td class="width-35">
                <sys:fileUpload path="file" value="${contract.file}" type="file"
                                uploadPath="/personnel/manager/contract"/>
            </td>

        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>培训员工管理</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">

        $(document).ready(function() {

        });
        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                jp.loading();
                jp.post("${ctx}/personnel/train/train/staffSave",$('#inputForm').serialize(),function(data){
                    if(data.success){
                        jp.getParent().refresh();
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                        jp.success(data.msg)

                    }else{
                        jp.error(data.msg);
                    }
                })
            }

        }
    </script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="trainRecord" class="form-horizontal">
    <form:hidden path="id"/>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
            <td class="width-35">
                <form:input path="user.name" htmlEscape="false"    class="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否合格：</label></td>
            <td class="width-35">
                <form:radiobuttons path="qualifyFlag" items="${fns:getDictList('qualify')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
            </td>
            <td class="width-15 active"></td>
            <td class="width-35" ></td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
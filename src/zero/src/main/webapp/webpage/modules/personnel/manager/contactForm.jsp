<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>通讯录管理</title>
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
                jp.post("${ctx}/personnel/manager/contact/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="contact" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="name" name="name.id" value="${contact.name.id}" labelName="name.name" labelValue="${contact.name.name}"
							 title="选择姓名" cssClass="form-control required" fieldLabels="部门|名字" fieldKeys="depart.name|name" searchLabels="部门|名字" searchKeys="depart.name|name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>个人邮箱：</label></td>
					<td class="width-35">
						<form:input path="email" htmlEscape="false"    class="form-control required email"/>
					</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>微信：</label></td>
					<td class="width-35">
						<form:input path="weChat" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>QQ号码：</label></td>
					<td class="width-35">
						<form:input path="qq" htmlEscape="false"    class="form-control required isQq"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>状态：</label></td>
					<td class="width-35">
						<form:radiobuttons path="status" items="${fns:getDictList('usage_state')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
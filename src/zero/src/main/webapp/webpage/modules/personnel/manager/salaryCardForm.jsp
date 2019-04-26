<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>工资卡管理</title>
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
                jp.post("${ctx}/personnel/manager/salaryCard/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="salaryCard" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名字：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="name" name="name.id" value="${salaryCard.name.id}" labelName="name.name" labelValue="${salaryCard.name.name}"
							 title="选择名字" cssClass="form-control required" fieldLabels="名字|部门" fieldKeys="name|depart.name" searchLabels="名字|部门" searchKeys="name|depart.name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>开户行：</label></td>
					<td class="width-35">
						<form:input path="bank" htmlEscape="false"  onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>工资卡号：</label></td>
					<td class="width-35">
						<form:input path="bankCard" htmlEscape="false"  onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>工资卡状态：</label></td>
					<td class="width-35">
						<form:radiobuttons path="salaryStatus" items="${fns:getDictList('usage_state')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:input path="remarks" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
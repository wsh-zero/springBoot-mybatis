<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>员工薪资配置管理</title>
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
                jp.post("${ctx}/personnel/salary/staSallary/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="staSallary" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right" ><font color="red">*</font>姓名：</label></td>
					<td class="width-35" >
						<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="name" name="name.id" value="${staSallary.name.id}" labelName="name.name" labelValue="${staSallary.name.name}"
							 title="选择姓名" disabled="disabled" cssClass="form-control required" fieldLabels="部门|姓名" fieldKeys="depart.name|name" searchLabels="姓名" searchKeys="name" ></sys:gridselect>
					</td>

					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>薪资账套：</label></td>
					<td class="width-35">
						<form:select path="allocation" id="getSa"  class="form-control required">
							<form:option value=""></form:option>
							<c:forEach items="${saAll}" var="o">
								<form:option value="${o.id}" label="">${o.allocation}</form:option>
							</c:forEach>
							<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						</form:select>
					</td>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
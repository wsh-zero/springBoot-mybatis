<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>员工薪酬管理</title>
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
                jp.post("${ctx}/personnel/salary/sallaryManager/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="sallaryManager" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="name" name="name.id" value="${sallaryManager.name.id}" labelName="name.name" labelValue="${sallaryManager.name.name}"
							 title="选择姓名" disabled="disabled" cssClass="form-control required" fieldLabels="部门|姓名" fieldKeys="depart.name|name" searchLabels="姓名" searchKeys="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>税前工资：</label></td>
					<td class="width-35">
						<form:input path="preWage" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>社保：</label></td>
					<td class="width-35">
						<form:input path="socialSecurity" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>公积金：</label></td>
					<td class="width-35">
						<form:input path="accumulation" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>职称：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/personnel/plan/titleBonus/data" id="title" name="title.id" value="${sallaryManager.title.id}" labelName="title.name" labelValue="${sallaryManager.title.name}"
							 title="选择职称" cssClass="form-control required" fieldLabels="职称|职称奖金" fieldKeys="name|bonus" searchLabels="职称" searchKeys="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>等级：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/personnel/plan/gradeBonus/data" id="grade" name="grade.id" value="${sallaryManager.grade.id}" labelName="grade.grade" labelValue="${sallaryManager.grade.grade}"
										title="选择等级" cssClass="form-control required" fieldLabels="等级|等级奖金" fieldKeys="grade|bonus" searchLabels="等级|等级奖金" searchKeys="grade" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资质奖金：</label></td>
					<td class="width-35">
						<form:input path="qualityBonus" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>基础绩效奖金：</label></td>
					<td class="width-35">
						<form:input path="displayBonus" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
		        <tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>学历奖金：</label></td>
					<td class="width-35">
						<form:input path="educationBonus" htmlEscape="false"    class="form-control required"/>
					</td>
		        </tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位管理管理</title>
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
                jp.post("${ctx}/personnel/plan/station/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="station" class="form-horizontal">
		<form:hidden path="id"/>
			<form:hidden path="number"/>
			<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>岗位编号：</label></td>
					<td class="width-35">
						<form:input path="gradeNumber" htmlEscape="false"   readonly="true"  class="form-control required "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>岗位名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>岗位类型：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/personnel/plan/jobCategory/data" id="jobType" name="jobType.id" value="${station.jobType.id}" labelName="jobType.jobType" labelValue="${station.jobType.jobType}"
							 title="选择岗位类型" cssClass="form-control required" fieldLabels="岗位类型" fieldKeys="jobType" searchLabels="岗位类型" searchKeys="jobType" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">工作流程：</label></td>
					<td class="width-35">
						<form:textarea path="process" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">岗位职责：</label></td>
					<td class="width-35">
						<form:textarea path="duty" htmlEscape="false"  class="form-control  "/>
					</td>
					<td class="width-15 active"><label class="pull-right">必备技能：</label></td>
					<td class="width-35">
						<form:textarea path="skill" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">岗位说明：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<%--<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属部门：</label></td>--%>
					<%--<td class="width-35">--%>
						<%--<sys:treeselect id="depart" name="depart.id" value="${station.depart.id}" labelName="depart.name" labelValue="${station.depart.name}"--%>
								<%--title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>--%>
					<%--</td>--%>
				</tr>

		 	</tbody>
		</table>
	</form:form>
</body>
</html>
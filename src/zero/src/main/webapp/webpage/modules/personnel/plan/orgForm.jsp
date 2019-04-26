<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>组织管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {

	        $('#establish').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});

		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/personnel/plan/org/save",$('#inputForm').serialize(),function(data){
                    if(data.success){
                        jp.getParent().refreshNode(data);
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
		<form:form id="inputForm" modelAttribute="org" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">父级编号：</label></td>
					<td class="width-35">
						<sys:treeselect id="parent" name="parent.id" value="${org.parent.id}" labelName="parent.name" labelValue="${org.parent.name}"
						title="父级编号" url="/personnel/plan/org/treeData" extId="${org.id}" cssClass="form-control " allowClear="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门编号：</label></td>
					<td class="width-35">
						<form:input path="departCode" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>组织类型：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/personnel/plan/orgType/data" id="orgType" name="orgType.id" value="${org.orgType.id}" labelName="orgType.name" labelValue="${org.orgType.name}"
							 title="选择组织类型" cssClass="form-control required" fieldLabels="组织类型" fieldKeys="name" searchLabels="" searchKeys="" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">负责人：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="master" name="master.id" value="${org.master.id}" labelName="master.name" labelValue="${org.master.name}"
							 title="选择负责人" cssClass="form-control " fieldLabels="" fieldKeys="" searchLabels="" searchKeys="" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">成立时间：</label></td>
					<td class="width-35">
						<div class='input-group form_datetime' id='establish'>
							<input type='text'  name="establish" class="form-control "  value="<fmt:formatDate value="${org.establish}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">所在地：</label></td>
					<td class="width-35">
						<sys:treeselect id="address" name="address" value="${org.address}" labelName="" labelValue=""
							title="区域" url="/sys/area/treeData" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">详细地址：</label></td>
					<td class="width-35">
						<form:input path="detailAddress" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
		</form:form>
</body>
</html>
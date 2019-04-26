<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>处罚记录管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {

	        $('#punishDate').datetimepicker({
				 format: "YYYY-MM-DD"
		    });
		});
		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/personnel/salary/penaltyRecord/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="penaltyRecord" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
					<td class="width-35">
						<sys:userselect id="name" name="name.id" value="${penaltyRecord.name.id}" labelName="name.name" labelValue="${penaltyRecord.name.name}"
							    cssClass="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>处罚日期：</label></td>
					<td class="width-35">
						<div class='input-group form_datetime' id='punishDate' >
							<input style="ime-mode:disabled" type='text'  name="punishDate" class="form-control required"    value="<fmt:formatDate value="${penaltyRecord.punishDate}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>处罚类型：</label></td>
					<td class="width-35">
						<form:select path="punishType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('admin_punish_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>罚款金额：</label></td>
					<td class="width-35">
						<form:input path="punishAmount" htmlEscape="false"   onkeyup="this.value=this.value.replace(/\s+/g,'')" class="form-control required negativeValue"/>
					</td>
				</tr>
				<tr>

					<td class="width-15 active"><label class="pull-right">说明：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
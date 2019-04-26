<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>工资发放时间管理</title>
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
                jp.post("${ctx}/personnel/salary/wageDate/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="wageDate" class="form-horizontal">
		<form:hidden path="id"/>
			<form:hidden path="scheduleJob"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>工资发放日期：</label></td>
					<td class="width-35">
						<form:input path="wageDate" htmlEscape="false"   max="28"  min="1" class="form-control required isIntGtZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>工资报表自动生成日期：</label></td>
					<td class="width-35">
						<form:input path="slipeDate" htmlEscape="false"   max="28"  min="1" class="form-control required isIntGtZero"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
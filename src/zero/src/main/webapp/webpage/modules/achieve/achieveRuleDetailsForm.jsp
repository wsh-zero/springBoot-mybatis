<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
	<title>绩效考核规则详情管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function () {

		});

		function save(fun) {
			var isValidate = jp.validateForm('#inputForm');//校验表单
			if (!isValidate) {
				return false;
			} else {
				jp.loading();
				jp.post("${ctx}/achieve/achieveRuleDetails/save", $('#inputForm').serialize(), function (data) {
					if (data.success) {
						if (fun) {
							fun();
						} else {
							jp.getParent().refresh()
						}
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
<form:form id="inputForm" modelAttribute="achieveRuleDetails" class="form-horizontal">
	<form:hidden path="id"/>
	<form:hidden path="achieveRuleId"/>
	<input id="type" name="type" type="hidden" value="0"/>
	<table class="table table-bordered">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">总分值:</label></td>
			<td class="width-35">
					${achieveRuleDetails.totalScore0}
			</td>
		</tr>
		<%--<tr>--%>
			<%--<td class="width-15 active"><label class="pull-right"><span style="color: red; ">*</span>考核指标:</label></td>--%>
			<%--<td class="width-35">--%>
				<%--<form:checkboxes path="achieveJudgeDetailsIds" items="${achieveJudgeDetails}" itemLabel="value"--%>
								 <%--itemValue="key"--%>
								 <%--htmlEscape="false" cssClass="i-checks required"/>--%>
				<%--<label class="error" for="achieveJudgeDetailsIds"></label>--%>

			<%--</td>--%>
		<%--</tr>--%>
		</tbody>
	</table>
</form:form>
</body>
</html>
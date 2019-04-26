<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>简历管理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/resumeinfo/resumeInfo");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">
				<a class="panelButton" href="${ctx}/resumeinfo/resumeInfo"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="resumeInfo" action="${ctx}/resumeinfo/resumeInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">简历编号：</label>
					<div class="col-sm-10">
						<form:input path="number" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">姓名：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性别：</label>
					<div class="col-sm-10">
						<form:input path="sex" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">手机号：</label>
					<div class="col-sm-10">
						<form:input path="mobile" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">应聘岗位：</label>
					<div class="col-sm-10">
						<form:input path="post" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">应聘部门：</label>
					<div class="col-sm-10">
						<form:input path="dept" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">面试官：</label>
					<div class="col-sm-10">
						<form:input path="viewer" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">面试时间：</label>
					<div class="col-sm-10">
						<form:input path="interviewTime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">面试记录：</label>
					<div class="col-sm-10">
						<form:input path="remark" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">面试结果：</label>
					<div class="col-sm-10">
						<form:input path="interviewResult" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">简历状态：</label>
					<div class="col-sm-10">
						<form:input path="status" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">简历内容：</label>
					<div class="col-sm-10">
						<a href="${ctx}/resumeinfo/resumeInfo/resumecontent?id=${resumeInfo.id}" target="_blank">查看简历</a>
<%--						<form:input path="content" htmlEscape="false" class="form-control "/>--%>
					</div>
				</div>
		<c:if test="${mode == 'add' || mode=='edit'}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
		</form:form>
		</div>
	</div>
	</div>
</div>
</div>
</body>
</html>
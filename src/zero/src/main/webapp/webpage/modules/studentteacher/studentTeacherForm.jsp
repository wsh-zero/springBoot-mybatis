<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>学生老师管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/studentteacher/studentTeacher");
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
				<a class="panelButton" href="${ctx}/studentteacher/studentTeacher"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="studentTeacher" action="${ctx}/studentteacher/studentTeacher/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">学生：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/t_student/t_Student/data" id="student" name="student.id" value="${studentTeacher.student.id}" labelName="student.name" labelValue="${studentTeacher.student.name}"
							 title="选择学生" cssClass="form-control " fieldLabels="学生姓名" fieldKeys="name" searchLabels="学生姓名" searchKeys="name" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">教师：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/t_teacher/t_Teacher/data" id="teahcer" name="teahcer.id" value="${studentTeacher.teahcer.id}" labelName="teahcer.name" labelValue="${studentTeacher.teahcer.name}"
							 title="选择教师" cssClass="form-control " fieldLabels="任课教师" fieldKeys="name" searchLabels="任课教师" searchKeys="name" ></sys:gridselect>
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
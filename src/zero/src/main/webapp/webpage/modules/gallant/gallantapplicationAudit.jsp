<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>招骋需求管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/gallant/gallantapplication");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});


            $("#agree").click(function () {
                jp.prompt("同意, 审批意见", function (message) {
                    jp.post("${ctx}/act/task/approveTask",
                        {
                            "taskId":"${gallantapplication.act.taskId}",
                            "taskName":"${gallantapplication.act.taskName}",
                            "taskDefKey":"${gallantapplication.act.taskDefKey}",
                            "procInsId":"${gallantapplication.act.procInsId}",
                            "procDefId":"${gallantapplication.act.procDefId}",
                            "flag":"yes",
                            "comment":message

                        },
                        function (data) {
								if(data.success){
									jp.success(data.msg);
									jp.go("${ctx}/act/task/todo")
								}
                        })
                })
            })


            $("#reject").click(function () {
                jp.prompt("驳回, 审批意见", function (message) {
                    jp.post("${ctx}/act/task/approveTask",
                        {
                            "taskId":"${gallantapplication.act.taskId}",
                            "taskName":"${gallantapplication.act.taskName}",
                            "taskDefKey":"${gallantapplication.act.taskDefKey}",
                            "procInsId":"${gallantapplication.act.procInsId}",
                            "procDefId":"${gallantapplication.act.procDefId}",
                            "flag":"no",
                            "comment":message

                        },
                        function (data) {

                            if(data.success){
								jp.success(data.msg);
								jp.go("${ctx}/act/task/todo")
                            }


                        })
                })
            })

			$("#publish").click(function () {
				jp.prompt("完成发布,发布意见", function (message) {
					jp.post("${ctx}/act/task/publish",
							{
								"taskId":"${gallantapplication.act.taskId}",
								"taskName":"${gallantapplication.act.taskName}",
								"taskDefKey":"${gallantapplication.act.taskDefKey}",
								"procInsId":"${gallantapplication.act.procInsId}",
								"procDefId":"${gallantapplication.act.procDefId}",
								"flag":"yes",
								"comment":message

							},
							function (data) {

								if(data.success){
									jp.success(data.msg);
									jp.go("${ctx}/act/task/todo")
								}


							})
				})
			})


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
				<a class="panelButton"  href="#"  onclick="history.go(-1)"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<div class="form-group text-center">
			<h3>${gallantapplication.act.taskName}</h3>
		</div>
		<form:form id="inputForm" modelAttribute="gallantapplication" action="${ctx}/gallant/gallantapplication/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>备注信息：</label>
					<div class="col-sm-10">
						${gallantapplication.remarks}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>所属公司：</label>
					<div class="col-sm-10">
						${gallantapplication.company}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>所属部门：</label>
					<div class="col-sm-10">
						${gallantapplication.department}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label">岗位名称：</label>
					<div class="col-sm-10">
						${gallantapplication.postname}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label">岗位数量：</label>
					<div class="col-sm-10">
						${gallantapplication.postnum}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label">岗位详情：</label>
					<div class="col-sm-10">
						${gallantapplication.postdetail}
					</div>
				</div>
			<hr>
		</form:form>

			<c:if test="${gallantapplication.act.taskName != 'HR' && !gallantapplication.act.finishTask && gallantapplication.act.isNextGatewaty}">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="form-group text-center">
						<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="同 意" />&nbsp;
						<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit" value="驳 回" />&nbsp;
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${gallantapplication.act.taskName == 'HR'}">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="form-group text-center">
						<input id="publish" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="招骋发布完成" />&nbsp;
					</div>
				</div>
			</div>
			</c:if>

			<c:if test="${not empty gallantapplication.id}">
				<act:flowChart procInsId="${gallantapplication.act.procInsId}"/>
				<act:histoicFlow procInsId="${gallantapplication.act.procInsId}" />
			</c:if>

		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>
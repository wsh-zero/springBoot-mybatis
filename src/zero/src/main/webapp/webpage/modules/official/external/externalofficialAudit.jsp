<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>对外公文管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/official/external/externalofficial");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});


            $("#agree").click(function () {
                jp.prompt("同意, 审批意见", function (message) {
                    jp.post("${ctx}/act/task/audit",
                        {
                            "taskId":"${externalofficial.act.taskId}",
                            "taskName":"${externalofficial.act.taskName}",
                            "taskDefKey":"${externalofficial.act.taskDefKey}",
                            "procInsId":"${externalofficial.act.procInsId}",
                            "procDefId":"${externalofficial.act.procDefId}",
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
                    jp.post("${ctx}/act/task/audit",
                        {
                            "taskId":"${externalofficial.act.taskId}",
                            "taskName":"${externalofficial.act.taskName}",
                            "taskDefKey":"${externalofficial.act.taskDefKey}",
                            "procInsId":"${externalofficial.act.procInsId}",
                            "procDefId":"${externalofficial.act.procDefId}",
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
				jp.prompt("完成,完成意见", function (message) {
					jp.post("${ctx}/act/task/publish",
							{
								"taskId":"${externalofficial.act.taskId}",
								"taskName":"${externalofficial.act.taskName}",
								"taskDefKey":"${externalofficial.act.taskDefKey}",
								"procInsId":"${externalofficial.act.procInsId}",
								"procDefId":"${externalofficial.act.procDefId}",
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
			<h3>${externalofficial.act.taskName}</h3>
		</div>
		<form:form id="inputForm" modelAttribute="externalofficial" action="${ctx}/official/external/externalofficial/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>备注信息：</label>
					<div class="col-sm-10">
						${externalofficial.remarks}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>公文编号：</label>
					<div class="col-sm-10">
						${externalofficial.externo}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>公文名称：</label>
					<div class="col-sm-10">
						${externalofficial.extername}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>发布部门：</label>
					<div class="col-sm-10">
						${externalofficial.publishdepart}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label">附件：</label>
					<div class="col-sm-10">
						<sys:fileUpload path="appendix"  readonly="true" value="${externalofficial.appendix}" type="file" uploadPath="/official/external/externalofficial"/>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>接收人/单位：</label>
					<div class="col-sm-10">
						${externalofficial.receive}
					</div>
				</div>
			<hr>
		</form:form>

		
			<c:if test="${externalofficial.act.taskName != '总裁' && !externalofficial.act.finishTask && externalofficial.act.isNextGatewaty}">
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

			<c:if test="${externalofficial.act.taskName == '总裁' && !externalofficial.act.finishTask && externalofficial.act.isNextGatewaty}">
				<div class="row">
					<div class="col-sm-3"></div>
					<div class="col-sm-6">
						<div class="form-group text-center">
							<input id="publish" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="完 成" />&nbsp;
						</div>
					</div>
				</div>
			</c:if>

			<c:if test="${not empty externalofficial.id}">
				<act:flowChart procInsId="${externalofficial.act.procInsId}"/>
				<act:histoicFlow procInsId="${externalofficial.act.procInsId}" />
			</c:if>

		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>
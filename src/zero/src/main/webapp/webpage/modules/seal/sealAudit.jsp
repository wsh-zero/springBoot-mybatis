<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用印管理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/seal/seal");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});


            $("#agree").click(function () {
                jp.prompt("同意, 审批意见", function (message) {
                    jp.post("${ctx}/act/task/audit",
                        {
                            "taskId":"${seal.act.taskId}",
                            "taskName":"${seal.act.taskName}",
                            "taskDefKey":"${seal.act.taskDefKey}",
                            "procInsId":"${seal.act.procInsId}",
                            "procDefId":"${seal.act.procDefId}",
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
                            "taskId":"${seal.act.taskId}",
                            "taskName":"${seal.act.taskName}",
                            "taskDefKey":"${seal.act.taskDefKey}",
                            "procInsId":"${seal.act.procInsId}",
                            "procDefId":"${seal.act.procDefId}",
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
								"taskId":"${seal.act.taskId}",
								"taskName":"${seal.act.taskName}",
								"taskDefKey":"${seal.act.taskDefKey}",
								"procInsId":"${seal.act.procInsId}",
								"procDefId":"${seal.act.procDefId}",
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


			$('#sealtime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
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
				<a class="panelButton"  href="#"  onclick="history.go(-1)"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<div class="form-group text-center">
			<h3>${seal.act.taskName}</h3>
		</div>
		<form:form id="inputForm" modelAttribute="seal" action="${ctx}/seal/seal/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>申请人：</label>
					<div class="col-sm-10">
						${seal.appperson}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>用途：</label>
					<div class="col-sm-10">
						${seal.purpose}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label">文件名称：</label>
					<div class="col-sm-10">
						 ${fns:getDictLabel(seal.filename, '', '')}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>用章类型：</label>
					<div class="col-sm-10">
						 ${seal.sealtype}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>盖章时间：</label>
					<div class="col-sm-10">
						<fmt:formatDate value="${seal.sealtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>盖章人：</label>
					<div class="col-sm-10">
						${seal.sealperson}
					</div>
				</div>
			<hr>
		</form:form>
			<%--<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="form-group text-center">
						<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="同 意" />&nbsp;
						<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit" value="驳 回" />&nbsp;
					</div>
				</div>
			</div>--%>
		
			<c:if test="${seal.act.taskName != '材料部' && !seal.act.finishTask && seal.act.isNextGatewaty}">
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
			<c:if test="${seal.act.taskName == '材料部'}">
				<div class="row">
					<div class="col-sm-3"></div>
					<div class="col-sm-6">
						<div class="form-group text-center">
							<input id="publish" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="完成" />&nbsp;
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${not empty seal.id}">
				<act:flowChart procInsId="${seal.act.procInsId}"/>
				<act:histoicFlow procInsId="${seal.act.procInsId}" />
			</c:if>

		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>
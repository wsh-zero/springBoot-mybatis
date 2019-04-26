<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>

<head>
	<title>对外公文管理</title>
	<meta name="decorator" content="ani" />
	<script type="text/javascript">
		$(document).ready(function () {
			jp.ajaxForm("#inputForm", function (data) {
				if (data.success) {
					jp.success(data.msg);
					jp.go("${ctx}" + data.body.targetUrl);
				} else {
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
							<a class="panelButton" href="#" onclick="history.go(-1)"><i class="ti-angle-left"></i>
								返回</a>
						</h3>
					</div>
					<div class="panel-body">
						<form:form id="inputForm" modelAttribute="externalofficial"
							action="${ctx}/official/external/externalofficial/save" method="post"
							class="form-horizontal">
							<form:hidden path="id" />
							<form:hidden path="act.taskId" />
							<form:hidden path="act.taskName" />
							<form:hidden path="act.taskDefKey" />
							<form:hidden path="act.procInsId" />
							<form:hidden path="act.procDefId" />
							<form:hidden id="flag" path="act.flag" />
							<div class="form-group text-center">
								<h3> 对外公文</h3>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>公文编号：
								</label>
								<div class="col-sm-10">
									<form:input path="externo" htmlEscape="false" class="form-control required" />
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>公文名称：
								</label>
								<div class="col-sm-10">
									<form:input path="extername" htmlEscape="false" class="form-control required" />
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>发布部门：
								</label>
								<div class="col-sm-10">
									<sys:treeselect id="publishdepart" name="publishdepart"
										value="${externalofficial.publishdepart}" labelName=""
										labelValue="${externalofficial.publishDepartName}" title="部门"
										url="/sys/office/treeData?type=2" cssClass="form-control required"
										allowClear="true" notAllowSelectParent="true" />
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">附件：</label>
								<div class="col-sm-10">
									<sys:fileUpload path="appendix" value="${externalofficial.appendix}" type="file"
										uploadPath="/official/external/externalofficial" />
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>接收人/单位：
								</label>
								<div class="col-sm-10">
									<form:input path="receive" htmlEscape="false" class="form-control required" />
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>备注信息：
								</label>
								<div class="col-sm-10">
									<form:textarea path="remarks" htmlEscape="false" rows="4"
										class="form-control required" />
								</div>
							</div>
							<hr>
							<div class="form-group">
								<div class="col-lg-3"></div>
								<c:if
									test="${externalofficial.act.taskDefKey ne '' && !externalofficial.act.finishTask && externalofficial.act.isNextGatewaty}">
									<div class="col-sm-6">
										<div class="form-group text-center">
											<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit"
												value="重新提交" onclick="$('#flag').val('yes')" />&nbsp;
											<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit"
												value="销毁申请" onclick="$('#flag').val('no')" />&nbsp;
										</div>
									</div>
								</c:if>
								<c:if test="${externalofficial.act.startTask}">
									<div class="col-lg-6">
										<div class="form-group text-center">
											<div>
												<button class="btn btn-primary btn-block btn-lg btn-parsley"
													data-loading-text="正在提交...">提 交</button>
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</form:form>
						<c:if test="${not empty externalofficial.id}">
							<act:flowChart procInsId="${externalofficial.act.procInsId}" />
							<act:histoicFlow procInsId="${externalofficial.act.procInsId}" />
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
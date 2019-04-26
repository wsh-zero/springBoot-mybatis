<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>

<head>
	<title>对内公文管理</title>
	<meta name="decorator" content="ani" />
	<script type="text/javascript">
		$(document).ready(function () {
			jp.ajaxForm("#inputForm", function (data) {
				if (data.success) {
					jp.success(data.msg);
					jp.go("${ctx}/official/internal/internalOfficial");
				} else {
					jp.error(data.msg);
					$("#inputForm").find("button:submit").button("reset");
				}
			});


			$("#agree").click(function () {
				jp.prompt("同意, 审批意见", function (message) {
					jp.post("${ctx}/act/task/audit", {
							"taskId": "${internalOfficial.act.taskId}",
							"taskName": "${internalOfficial.act.taskName}",
							"taskDefKey": "${internalOfficial.act.taskDefKey}",
							"procInsId": "${internalOfficial.act.procInsId}",
							"procDefId": "${internalOfficial.act.procDefId}",
							"flag": "yes",
							"comment": message

						},
						function (data) {
							if (data.success) {
								jp.success(data.msg);
								jp.go("${ctx}/act/task/todo")
							}
						})
				})
			})


			$("#reject").click(function () {
				jp.prompt("驳回, 审批意见", function (message) {
					jp.post("${ctx}/act/task/audit", {
							"taskId": "${internalOfficial.act.taskId}",
							"taskName": "${internalOfficial.act.taskName}",
							"taskDefKey": "${internalOfficial.act.taskDefKey}",
							"procInsId": "${internalOfficial.act.procInsId}",
							"procDefId": "${internalOfficial.act.procDefId}",
							"flag": "no",
							"comment": message

						},
						function (data) {

							if (data.success) {
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
							<a class="panelButton" href="#" onclick="history.go(-1)"><i class="ti-angle-left"></i>
								返回</a>
						</h3>
					</div>
					<div class="panel-body">
						<div class="form-group text-center">
							<h3>${internalOfficial.act.taskName}</h3>
						</div>
						<form:form id="inputForm" modelAttribute="internalOfficial"
							action="${ctx}/official/internal/internalOfficial/save" method="post"
							class="form-horizontal">
							<form:hidden path="id" />
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>公文类型：
								</label>
								<div class="col-sm-10">
									${internalOfficial.officialType.typename}
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>公文编号：
								</label>
								<div class="col-sm-10">
									${internalOfficial.internalno}
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>公文名称：
								</label>
								<div class="col-sm-10">
									${internalOfficial.internalname}
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">附件：</label>
								<div class="col-sm-10">
									<sys:fileUpload path="appendix" readonly="true" value="${internalOfficial.appendix}"
										type="file" uploadPath="/official/internal/internalOfficial" />
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>接收人：
								</label>
								<div class="col-sm-10">
									${internalOfficial.targetUsers}
								</div>
							</div>
							<hr>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<font color="red">*</font>备注信息：
								</label>
								<div class="col-sm-10">
									${internalOfficial.remarks}
								</div>
							</div>
							<hr>
							<c:if test="${view eq 'view'}">
								<div class="form-group">
									<label class="col-sm-2 control-label">读取状态：</label>

									<div class="form-group">
										<table>
											<tr>

												<td class="width-35" colspan="3">
													<table id="contentTable"
														class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
														<thead>
															<tr>
																<th>接受人</th>
																<th>阅读状态</th>
																<th>阅读时间</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${internalOfficial.targets}" var="target">
																<tr>
																	<td>
																		${target.userId.name}
																	</td>
																	<td>
																		<c:if test="${target.readed == '0'}">
																			<span style="color: #ff371e;">未读</span>
																		</c:if>
																		<c:if test="${target.readed == '1'}">
																			<span style="color: #38ff73;">已读</span>
																		</c:if>
																	</td>
																	<td>
																		<c:if test="${target.readed == '1'}">
																			<fmt:formatDate value="${target.readTime}"
																				pattern="yyyy-MM-dd HH:mm:ss" />
																		</c:if>
																		<c:if test="${target.readed == '0'}">
																			<span style="color: #ff371e;">----</span>
																		</c:if>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
											</tr>
										</table>
									</div>

								</div>
							</c:if>
						</form:form>


						<c:if
							test="${internalOfficial.act.taskDefKey != '' && !internalOfficial.act.finishTask && internalOfficial.act.isNextGatewaty}">
							<div class="row">
								<div class="col-sm-3"></div>
								<div class="col-sm-6">
									<div class="form-group text-center">
										<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit"
											value="同 意" />&nbsp;
										<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit"
											value="驳 回" />&nbsp;
									</div>
								</div>
							</div>
						</c:if>
						<c:if
							test="${internalOfficial.act.taskDefKey != '' && !internalOfficial.act.finishTask && !internalOfficial.act.isNextGatewaty}">
							<div class="row">
								<div class="col-sm-3"></div>
								<div class="col-sm-6">
									<div class="form-group text-center">
										<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit"
											value="办 理" />&nbsp;
									</div>
								</div>
							</div>
						</c:if>

						<c:if test="${not empty internalOfficial.id}">
							<act:flowChart procInsId="${internalOfficial.act.procInsId}" />
							<act:histoicFlow procInsId="${internalOfficial.act.procInsId}" />
						</c:if>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
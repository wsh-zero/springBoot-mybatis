<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会议纪要管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/official/meeting/meetsummary");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

	        $('#startTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#endTime').datetimepicker({
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
				<a class="panelButton" href="${ctx}/official/meeting/meetsummary${isSelf?'/self':'' }"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="meetsummary" action="${ctx}/official/meeting/meetsummary/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>会议类型：</label>
				<div class="col-sm-10">
					<form:select path="meettype" class="form-control required">
						<form:option value="" label="">--请选择--</form:option>
						<form:option value="紧急会议" label="">紧急会议</form:option>
						<form:option value="重要会议" label="">重要会议</form:option>
						<form:option value="一般会议" label="">一般会议</form:option>
					</form:select>
				</div>
			</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>会议主题：</label>
					<div class="col-sm-10">
						<form:input path="meettitle" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>会议内容：</label>
					<div class="col-sm-10">
						<form:textarea path="meetcontent" htmlEscape="false"  rows="10"  class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">附件：</label>
					<div class="col-sm-10">
						<sys:fileUpload path="appendix"  value="${meetsummary.appendix}" type="file" uploadPath="/official/meeting/meetsummary"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>开始日期：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='startTime'>
							<input type='text'  name="startTime" class="form-control required"  value="<fmt:formatDate value="${meetsummary.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>结束日期：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='endTime'>
							<input type='text'  name="endTime" class="form-control required"  value="<fmt:formatDate value="${meetsummary.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
			<c:if test="${meetsummary.status ne '1'}">
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>参会人：</label>
					<div class="col-sm-10">
						<sys:userselect id="meetperson" name="receivePersons" value="${meetsummary.receivePersons}" labelName="meetRecordNames" labelValue="${meetsummary.meetRecordNames}"
							    cssClass="form-control required" isMultiSelected="true"/>
					</div>
				</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>状态：</label>
					<%--<div class="col-sm-10">
						<form:execute path="status" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:execute>
					</div>--%>
				<div class="col-sm-10">
					<form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
				</div>
			</div>
			</c:if>
			<c:if test="${meetsummary.status eq '1'}">
				<div class="form-group">
					<table>
						<tr>
							<td  class="width-15 active">	<label class="pull-right">接受人：</label></td>
							<td class="width-35" colspan="3"><table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
								<thead>
								<tr>
									<th>接受人</th>
									<th>接受部门</th>
									<th>阅读状态</th>
									<th>阅读时间</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${meetsummary.meetsummmaryRecordList}" var="meetRecord">
									<tr>
										<td>
												${meetRecord.user.name}
										</td>
										<td>
												${meetRecord.user.office.name}
										</td>
										<td>
												${fns:getDictLabel(meetRecord.readFlag, 'oa_notify_read', '')}
										</td>
										<td>
											<fmt:formatDate value="${meetRecord.readDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
								已查阅：${meetsummary.readNum} &nbsp; 未查阅：${meetsummary.unReadNum} &nbsp; 总共：${meetsummary.readNum + meetsummary.unReadNum}</td>
						</tr>
					</table>
				</div>
			</c:if>
			<c:if test="${meetsummary.status ne '1'}">
				<shiro:hasPermission name="official:meeting:meetsummary:edit">
					<div class="col-lg-3"></div>
					<div class="col-lg-6">
						<div class="form-group text-center">
							<div>
								<button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
							</div>
						</div>
					</div>
				</shiro:hasPermission>
			</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>
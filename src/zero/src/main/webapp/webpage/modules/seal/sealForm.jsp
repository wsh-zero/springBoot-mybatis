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
					jp.go("${ctx}"+ data.body.targetUrl);
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

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
		<form:form id="inputForm" modelAttribute="seal" action="${ctx}/seal/seal/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag" />
		<div class="form-group text-center">
			<h3> 用印管理</h3>
		</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>申请人：</label>
					<div class="col-sm-10">
						<form:input path="appperson" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>用途：</label>
					<div class="col-sm-10">
						<form:input path="purpose" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label">文件名称：</label>
					<div class="col-sm-10">
						<form:input path="filename" htmlEscape="false"    class="form-control "/>
						<%--<form:select path="filename" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>--%>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>用章类型：</label>
					<div class="col-sm-10">
						<form:select path="sealtype" class="form-control ">
							<form:option value="" label="">--请选择类型--</form:option>
							<form:option value="公章" label="">公章</form:option>
							<form:option value="合同章" label="">合同章</form:option>
							<form:option value="法人章" label="">法人章</form:option>
						</form:select>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>盖章时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='sealtime'>
							<input type='text'  name="sealtime" class="form-control required"  value="<fmt:formatDate value="${seal.sealtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>盖章人：</label>
					<div class="col-sm-10">
						<form:input path="sealperson" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
			<hr>
		<div class="form-group">
		<div class="col-lg-3"></div>
		<c:if test="${seal.act.taskDefKey ne '' && !seal.act.finishTask && seal.act.isNextGatewaty}">
			<div class="col-sm-6">
				<div class="form-group text-center">
					<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="重新提交" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
				</div>
			</div>
		</c:if>
		<c:if test="${seal.act.startTask}">
		<div class="col-lg-6">
			 <div class="form-group text-center">
				 <div>
					 <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
				 </div>
			 </div>
		</div>
		</c:if>
		</div>
		</form:form>
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
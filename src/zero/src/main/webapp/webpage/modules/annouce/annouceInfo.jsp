<%@ page import="com.jeeplus.modules.annouce.entity.Annouce" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>公告信息详情</title>
	<meta name="decorator" content="ani"/>
	<%@include file="/webpage/include/summernote.jsp" %>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/annouce/annouce/self"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="annouce" action="${ctx}/annouce/annouce/save" method="post" class="form-horizontal">

		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>标题：</label>
					<div class="col-sm-10">
						<form:label path="title" htmlEscape="false" class="form-control required">
							${annouce.title}
						</form:label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>内容：</label>
					<div class="col-sm-10">
						<%--<form:textarea path="content" htmlEscape="false" rows="20"   class="form-control required"/>--%>
						<%--<input type="hidden" name="content" value=" ${annouce.content}"/>
						<div id="contentid">
								${fns:unescapeHtml(annouce.content)}
						</div>--%>
						<div id="contentInfo">
						</div>

					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">附件：</label>
					<div class="col-sm-10">
						<sys:fileUpload path="appendix"  value="${annouce.appendix}" type="file" uploadPath="/annouce/annouce"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>开始日期：</label>
					<div class="col-sm-10">
						<form:label path="title" htmlEscape="false" class="form-control required">
							<fmt:formatDate value="${annouce.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</form:label>
					</div>
				</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>结束日期：</label>
				<div class="col-sm-10">
					<form:label path="title" htmlEscape="false" class="form-control required">
						<fmt:formatDate value="${annouce.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</form:label>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>类型：</label>
				<div class="col-sm-10">
					<form:label path="title" htmlEscape="false" class="form-control required">
						${fns:getDictList('annoucetype')[annouce.type].label}
					</form:label>
				</div>
			</div>
			<c:if test="${annouce.status ne '1'}">
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>接收人：</label>
					<div class="col-sm-10">
						<sys:userselect id="receivePerson" name="receivePersons" value="${annouce.receivePersons}" labelName="annouceRecordNames" labelValue="${annouce.annouceRecordNames}"
							    cssClass="form-control required" isMultiSelected="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>状态：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
			</c:if>
		</form:form>

		</div>				
	</div>
	</div>
</div>
</div>

<script>
	let content = $('<div>').html('${annouce.content}').text();
	$("#contentInfo").html(content);
</script>
</body>
</html>
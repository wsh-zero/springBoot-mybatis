<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合作个人资质管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/aptitude/cooper/cooperaptitude");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

	        $('#issuetime').datetimepicker({
				 format: "YYYY-MM-DD"
		    });
	        $('#expiretime').datetimepicker({
				 format: "YYYY-MM-DD"
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
				<a class="panelButton" href="${ctx}/aptitude/cooper/cooperaptitude"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="cooperaptitude" action="${ctx}/aptitude/cooper/cooperaptitude/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>资质编号：</label>
					<div class="col-sm-10">
						<form:input path="aptitudeno" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font> 资质名称：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/aptitude/param/aptitudeparam/data" id="aptitudename" name="aptitudename.id" value="${cooperaptitude.aptitudename.id}" labelName="aptitudename.aptitudename" labelValue="${cooperaptitude.aptitudename.aptitudename}"
							 title="选择 资质名称" cssClass="form-control required" fieldLabels="资质名称|级别" fieldKeys="aptitudename|grade" searchLabels="资质名称" searchKeys="aptitudename" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>颁发单位：</label>
					<div class="col-sm-10">
						<form:input path="issueunit" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>颁发日期：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='issuetime'>
							<input type='text'  name="issuetime" class="form-control required"  value="<fmt:formatDate value="${cooperaptitude.issuetime}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>有效期限(月)：</label>
					<div class="col-sm-10">
						<form:input path="validtime" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>资质扫描件：</label>
					<div class="col-sm-10">
						<sys:fileUpload path="scanning"  value="${cooperaptitude.scanning}" type="file" uploadPath="/aptitude/cooper/cooperaptitude"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>证书持有人：</label>
					<div class="col-sm-10">
						<form:input path="certificate" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<%--<div class="form-group">
			<label class="col-sm-2 control-label"><font color="red">*</font>等级：</label>
			<div class="col-sm-10">
				<form:input path="grade" htmlEscape="false"    class="form-control required"/>
			</div>
		</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>到期日期：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='expiretime'>
							<input type='text'  name="expiretime" class="form-control required"  value="<fmt:formatDate value="${cooperaptitude.expiretime}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">备注信息：</label>
				<div class="col-sm-10">
					<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资产出库管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/asset/getstore/getstoreasset");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

	        $('#buytime').datetimepicker({
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
				<a class="panelButton" href="${ctx}/asset/getstore/getstoreasset"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="getstoreasset" action="${ctx}/asset/getstore/getstoreasset/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>资产名称：</label>
					<div class="col-sm-10">
						<form:input path="assetname" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>规格/型号：</label>
					<div class="col-sm-10">
						<form:input path="specimodel" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>购买时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='buytime'>
							<input type='text'  name="buytime" class="form-control required"  value="<fmt:formatDate value="${getstoreasset.buytime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>金额：</label>
					<div class="col-sm-10">
						<form:input path="amount" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>数量：</label>
					<div class="col-sm-10">
						<form:input path="number" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>存放地点：</label>
					<div class="col-sm-10">
						<form:input path="address" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>使用人：</label>
					<div class="col-sm-10">
						<sys:userselect id="useperson" name="useperson" value="${getstoreasset.userpersonIds}" labelName="" labelValue="${getstoreasset.userpersonNames}"
							    cssClass="form-control required" isMultiSelected="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>使用状态：</label>
					<div class="col-sm-10">
						<form:select path="usestatus" class="form-control required">
							<form:option value="" label="">--请选择状态--</form:option>
							<form:option value="未使用" label="">未使用</form:option>
							<form:option value="已使用" label="">已使用</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>责任人：</label>
					<div class="col-sm-10">
						<sys:userselect id="personliable" name="personliable" value="${getstoreasset.personliable}" labelName="psersonlianame" labelValue="${getstoreasset.psersonlianame}"
							    cssClass="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>财务折旧年限（年）：</label>
					<div class="col-sm-10">
						<form:input path="yearnum" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>资产净值：</label>
					<div class="col-sm-10">
						<form:input path="assetvalue" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>备注信息：</label>
				<div class="col-sm-10">
					<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control required"/>
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
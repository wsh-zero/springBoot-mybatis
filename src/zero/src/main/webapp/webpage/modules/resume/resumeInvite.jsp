<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>简历管理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/resume/resumemanager");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

			$('#auditiontime').datetimepicker({
				format: "YYYY-MM-DD HH:mm:ss"
			});
		});

		function save() {
			var isValidate = jp.validateForm('#inputForm');//校验表单
			if(!isValidate){
				return false;
			}else{
				jp.loading();
				jp.post("${ctx}/resume/resumemanager/save",$('#inputForm').serialize(),function(data){
					if(data.success){
						jp.getParent().refresh();
						var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
						parent.layer.close(dialogIndex);
						jp.success(data.msg)

					}else{
						jp.error(data.msg);
					}
				})
			}

		}
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="resumemanager" action="${ctx}/resume/resumemanager/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4" readonly="true"   class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">简历编号：</label>
					<div class="col-sm-10">
						<form:input path="resumeno" htmlEscape="false"  readonly="true"   class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">求职者姓名：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false"  readonly="true"  class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性别：</label>
					<div class="col-sm-10">
						<form:input path="sex" htmlEscape="false" readonly="true"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">手机号码：</label>
					<div class="col-sm-10">
						<form:input path="telphone" htmlEscape="false"  readonly="true"  class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">应聘部门：</label>
					<div class="col-sm-10">
						<form:select path="deptno" id="getoffice"  class="form-control required">
							<form:option value="">--请选择部门--</form:option>
							<c:forEach items="${offices}" var="o">
							<form:option value="${o.id}" label="">${o.name}</form:option>
							</c:forEach>
							<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">面试官：</label>
					<div class="col-sm-10">
						<form:select path="interviewer" class="form-control required">
							<form:option value="">--请选择面试官--</form:option>
							<c:forEach items="${users}" var="user">
								<form:option value="${user.id}" label="">${user.name}</form:option>
							</c:forEach>
							<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">面试时间：</label>
					<div class="col-sm-10">
						<%--<form:input path="auditiontime" htmlEscape="false"    class="form-control "/>--%>
							<div class='input-group form_datetime' id='auditiontime'>
								<input type='text'  name="auditiontime" class="form-control required"  value="<fmt:formatDate value="${resumemanager.auditiontime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
								<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
							</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">面试记录：</label>
					<div class="col-sm-10">
						<form:input path="record" htmlEscape="false" readonly="true"   class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">面试结果：</label>
					<div class="col-sm-10">
						<form:input path="result" htmlEscape="false"  readonly="true"  class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">简历状态：</label>
					<div class="col-sm-10">
						<input type="hidden" name="flag" value="待面试">
						<form:input path="status" htmlEscape="false"  readonly="true"  class="form-control "/>
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
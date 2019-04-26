<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>工资条管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/personnel/salary/paySlip");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

	        $('#releaseTime').datetimepicker({
				 format: "YYYY-MM"
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
				<a class="panelButton" href="${ctx}/personnel/salary/paySlip"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="paySlip" action="${ctx}/personnel/salary/paySlip/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">姓名：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="name" name="name.id" value="${paySlip.name.id}" labelName="name.name" labelValue="${paySlip.name.name}"
							 title="选择姓名" cssClass="form-control " fieldLabels="" fieldKeys="" searchLabels="" searchKeys="" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">发放月：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='releaseTime'>
							<input type='text'  name="releaseTime" class="form-control "  value="<fmt:formatDate value="${paySlip.releaseTime}" pattern="yyyy-MM"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">工资状态：</label>
					<div class="col-sm-10">
						<form:select path="status" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('salary_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">税前工资：</label>
					<div class="col-sm-10">
						<form:input path="preWage" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">社保：</label>
					<div class="col-sm-10">
						<form:input path="socialSecurity" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">公积金：</label>
					<div class="col-sm-10">
						<form:input path="accumulation" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">职称奖金：</label>
					<div class="col-sm-10">
						<form:input path="titleBonus" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">等级奖金：</label>
					<div class="col-sm-10">
						<form:input path="gradeBonus" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">资质奖金：</label>
					<div class="col-sm-10">
						<form:input path="qualityBonus" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">学历奖金：</label>
					<div class="col-sm-10">
						<form:input path="educationBonus" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">基础绩效奖金：</label>
					<div class="col-sm-10">
						<form:input path="displayBonus" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">行政处罚：</label>
					<div class="col-sm-10">
						<form:input path="punish" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">个税：</label>
					<div class="col-sm-10">
						<form:input path="tax" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">考勤：</label>
					<div class="col-sm-10">
						<form:input path="attendance" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">实得月工资：</label>
					<div class="col-sm-10">
						<form:input path="wage" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">绩效得分：</label>
					<div class="col-sm-10">
						<form:input path="achPoint" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">实得绩效奖金：</label>
					<div class="col-sm-10">
						<form:input path="achBonus" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">合计：</label>
					<div class="col-sm-10">
						<form:input path="total" htmlEscape="false"    class="form-control "/>
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
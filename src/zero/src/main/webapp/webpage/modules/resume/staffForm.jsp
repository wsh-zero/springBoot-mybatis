<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>员工信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/personnel/manager/staff");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

	        $('#birthDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#entryDate').datetimepicker({
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
				<a class="panelButton" href="${ctx}/personnel/manager/staff"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="staff" action="${ctx}/personnel/manager/staff/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>员工姓名：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>员工编号：</label>
					<div class="col-sm-10">
						<form:input path="code" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>公司：</label>
					<div class="col-sm-10">
						<sys:treeselect id="company" name="company.id" value="${staff.company.id}" labelName="company.name" labelValue="${staff.company.name}"
							title="部门" url="/sys/office/treeData?type=1" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>所属部门：</label>
					<div class="col-sm-10">
						<sys:treeselect id="depart" name="depart.id" value="${staff.depart.id}" labelName="depart.name" labelValue="${staff.depart.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>性别：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>岗位：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/plan/station/data" id="station" name="station.id" value="${staff.station.id}" labelName="station.name" labelValue="${staff.station.name}"
							 title="选择岗位" cssClass="form-control required" fieldLabels="部门|岗位类型|岗位" fieldKeys="name|jobType|name" searchLabels="部门|岗位类型|岗位" searchKeys="name|jobType|name" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">上级领导：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="leader" name="leader.id" value="${staff.leader.id}" labelName="leader.name" labelValue="${staff.leader.name}"
							 title="选择上级领导" cssClass="form-control " fieldLabels="部门|名字" fieldKeys="depart|name" searchLabels="部门|名字" searchKeys="depart|name" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>出生日期：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='birthDate'>
							<input type='text'  name="birthDate" class="form-control required"  value="<fmt:formatDate value="${staff.birthDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>身份证号：</label>
					<div class="col-sm-10">
						<form:input path="idCard" htmlEscape="false"    class="form-control required isIdCardNo"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>婚姻状况：</label>
					<div class="col-sm-10">
						<form:select path="marriage" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('marriage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>学历：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/plan/education/data" id="education" name="education.id" value="${staff.education.id}" labelName="education.educate" labelValue="${staff.education.educate}"
							 title="选择学历" cssClass="form-control required" fieldLabels="学历" fieldKeys="educate" searchLabels="学历" searchKeys="educate" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>员工职级：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/plan/rank/data" id="rank" name="rank.id" value="${staff.rank.id}" labelName="rank.rankName" labelValue="${staff.rank.rankName}"
							 title="选择员工职级" cssClass="form-control required" fieldLabels="职级" fieldKeys="rankName" searchLabels="职级" searchKeys="rankName" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>员工状态：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/manage/staffStatus/data" id="status" name="status.id" value="${staff.status.id}" labelName="status.status" labelValue="${staff.status.status}"
							 title="选择员工状态" cssClass="form-control required" fieldLabels="状态" fieldKeys="status" searchLabels="状态" searchKeys="status" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>员工类型：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/manage/staffType/data" id="staffType" name="staffType.id" value="${staff.staffType.id}" labelName="staffType.type" labelValue="${staff.staffType.type}"
							 title="选择员工类型" cssClass="form-control required" fieldLabels="员工类型" fieldKeys="type" searchLabels="员工类型" searchKeys="type" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系方式：</label>
					<div class="col-sm-10">
						<form:input path="contactType" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">考勤类型：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/attendance/attendanceType/data" id="attendance" name="attendance.id" value="${staff.attendance.id}" labelName="attendance.name" labelValue="${staff.attendance.name}"
							 title="选择考勤类型" cssClass="form-control " fieldLabels="考勤类型" fieldKeys="name" searchLabels="考勤类型" searchKeys="name" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">入职时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='entryDate'>
							<input type='text'  name="entryDate" class="form-control "  value="<fmt:formatDate value="${staff.entryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">合同类型：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/manage/contractType/data" id="contractType" name="contractType.id" value="${staff.contractType.id}" labelName="contractType.name" labelValue="${staff.contractType.name}"
							 title="选择合同类型" cssClass="form-control " fieldLabels="合同类型" fieldKeys="name" searchLabels="" searchKeys="" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">合同编号：</label>
					<div class="col-sm-10">
						<form:input path="contractCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">试用期到期日：</label>
				<div class="col-sm-10">
					<form:input path="trailEnd" htmlEscape="false"    class="form-control "/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">合同开始日期：</label>
				<div class="col-sm-10">
					<div class='input-group form_datetime' id='contractStart'>
						<input type='text'  name="contractStart" class="form-control "  value="<fmt:formatDate value="${staff.contractStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">合同结束日期：</label>
				<div class="col-sm-10">
					<div class='input-group form_datetime' id='contractEnd'>
						<input type='text'  name="contractEnd" class="form-control "  value="<fmt:formatDate value="${staff.contractEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
					</div>
				</div>
			</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">保密协议编号：</label>
					<div class="col-sm-10">
						<form:input path="secretCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">协议起始日：</label>
				<div class="col-sm-10">
					<div class='input-group form_datetime' id='secretStart'>
						<input type='text'  name="secretStart" class="form-control "  value="<fmt:formatDate value="${staff.secretStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">协议终止日：</label>
				<div class="col-sm-10">
					<div class='input-group form_datetime' id='secretEnd'>
						<input type='text'  name="secretEnd" class="form-control "  value="<fmt:formatDate value="${staff.secretEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
					</div>
				</div>
			</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">签订次数：</label>
					<div class="col-sm-10">
						<form:input path="signNumber" htmlEscape="false"    class="form-control "/>
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
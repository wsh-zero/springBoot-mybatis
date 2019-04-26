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
				 format: "YYYY-MM-DD"
		    });
	        $('#entryDate').datetimepicker({
				 format: "YYYY-MM-DD"
		    });
			$('#graduationTime').datetimepicker({
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
				<a class="panelButton" href="${ctx}/personnel/manager/staff"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="staff" action="${ctx}/personnel/manager/staff/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
			<form:hidden path="number"/>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>员工姓名：</label>
				<div class="col-sm-10">
					<form:input path="name" htmlEscape="false"   class="form-control required"   onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>员工编号：</label>
				<div class="col-sm-10">
					<form:input path="code" htmlEscape="false"   readonly="true" class="form-control required"/>
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
									title="部门" extId="${depart.id}" url="/sys/office/treeData" cssClass="form-control required" notAllowSelectParent="false" allowClear="true"/>
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
									title="选择岗位" cssClass="form-control required" fieldLabels="岗位类别|岗位" fieldKeys="jobType.jobType|name" searchLabels="岗位类别|岗位" searchKeys="jobType.jobType|name" ></sys:gridselect>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">上级领导：</label>
				<div class="col-sm-10">
					<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="leader" name="leader.id" value="${staff.leader.id}" labelName="leader.name" labelValue="${staff.leader.name}"
									title="选择上级领导" cssClass="form-control required" fieldLabels="部门|名字" fieldKeys="depart.name|name" searchLabels="部门|名字" searchKeys="depart.name|name" ></sys:gridselect>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>出生日期：</label>
				<div class="col-sm-10">
					<div class='input-group form_datetime' id='birthDate'>
						<input type='text'  name="birthDate" class="form-control required"  value="<fmt:formatDate value="${staff.birthDate}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>身份证号：</label>
				<div class="col-sm-10">
					<form:input path="idCard" htmlEscape="false"    class="form-control required"/>
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
					<form:select path="education" id="getEdu"  class="form-control required">
						<form:option value=""></form:option>
						<c:forEach items="${education}" var="o">
							<form:option value="${o.id}" label="">${o.educate}</form:option>
						</c:forEach>
						<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>员工职级：</label>
				<div class="col-sm-10">
					<form:select path="rank" id="getRank"  class="form-control required">
						<form:option value=""></form:option>
						<c:forEach items="${rank}" var="o">
							<form:option value="${o.id}" label="">${o.rankName}</form:option>
						</c:forEach>
						<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>员工状态：</label>
				<div class="col-sm-10">
					<form:select path="status" id="getStatus"  class="form-control required">
						<form:option value=""></form:option>
						<c:forEach items="${status}" var="o">
							<form:option value="${o.id}" label="">${o.status}</form:option>
						</c:forEach>
						<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>员工类型：</label>
				<div class="col-sm-10">
					<form:select path="staffType" id="getType"  class="form-control required">
						<form:option value=""></form:option>
						<c:forEach items="${type}" var="o">
							<form:option value="${o.id}" label="">${o.type}</form:option>
						</c:forEach>
						<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>手机号码：</label>
				<div class="col-sm-10">
					<form:input path="contactType" htmlEscape="false"    class="form-control isMobile"/>
				</div>
			</div>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label"><font color="red">*</font>考勤类型：</label>--%>
				<%--<div class="col-sm-10">--%>
					<%--<sys:gridselect url="${ctx}/personnel/attendance/attendanceType/data" id="attendance" name="attendance.id" value="${staff.attendance.id}" labelName="attendance.name" labelValue="${staff.attendance.name}"--%>
									<%--title="选择考勤类型" cssClass="form-control" fieldLabels="考勤类型" fieldKeys="name" searchLabels="考勤类型" searchKeys="name" ></sys:gridselect>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>入职时间：</label>
				<div class="col-sm-10">
					<div class='input-group form_datetime' id='entryDate'>
						<input type='text'  name="entryDate" class="form-control required"  value="<fmt:formatDate value="${staff.entryDate}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>民族：</label>
				<div class="col-sm-10">
					<form:select path="nation" class="form-control required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>毕业学校：</label>
				<div class="col-sm-10">
					<form:input path="graduation" htmlEscape="false"   onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  class="form-control required"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>专业：</label>
				<div class="col-sm-10">
					<form:input path="major" htmlEscape="false"   onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false; " class="form-control required"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>籍贯：</label>
				<div class="col-sm-10">
					<sys:treeselect id="register" name="register.id" value="${staff.register.id}" labelName="register.name" labelValue="${staff.register.name}"
									title="区域" url="/sys/area/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="flase"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>户口：</label>
				<div class="col-sm-10">
					<sys:treeselect id="nativePlace" name="nativePlace.id" value="${staff.nativePlace.id}" labelName="nativePlace.name" labelValue="${staff.nativePlace.name}"
									title="区域" url="/sys/area/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="flase"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">详细地址：</label>
				<div class="col-sm-10">
					<form:input path="detailPlace" htmlEscape="false"   onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" class="form-control "/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>毕业时间：</label>
				<div class="col-sm-10">
					<div class='input-group form_datetime' id='graduationTime'>
						<input type='text'  name="graduationTime" class="form-control required"  value="<fmt:formatDate value="${staff.graduationTime}" pattern="yyyy-MM-dd"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">社保号码：</label>
				<div class="col-sm-10">
					<form:input path="socialSecurity" htmlEscape="false"    onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" class="form-control "/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>政治面貌：</label>
				<div class="col-sm-10">
					<form:select path="political" class="form-control required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('political')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
			<c:if test="${mode == 'view' }">
			<div class="form-group">
				<label class="col-sm-2 control-label">本单位工龄：</label>
				<div class="col-sm-10">
					<form:input path="workYear" htmlEscape="false"    class="form-control"/>
				</div>
			</div>
			</c:if>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label"><font color="red">*</font>合同类型：</label>--%>
				<%--<div class="col-sm-10">--%>
					<%--<sys:gridselect url="${ctx}/personnel/manage/contractType/data" id="contractType" name="contractType.id" value="${staff.contractType.id}" labelName="contractType.name" labelValue="${staff.contractType.name}"--%>
									<%--title="选择合同类型" cssClass="form-control" fieldLabels="合同类型" fieldKeys="name" searchLabels="合同类型" searchKeys="name" ></sys:gridselect>--%>
				<%--</div>--%>
			<%--</div>--%>
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
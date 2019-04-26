<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>员工信息管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="staffList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">员工信息列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="staff" class="form form-horizontal well clearfix">
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="员工姓名：">员工姓名：</label>
					<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control"/>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="员工编号：">员工编号：</label>
					<form:input path="code" htmlEscape="false" maxlength="64"  class=" form-control"/>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="所属部门：">所属部门：</label>
				<sys:treeselect id="depart" name="depart.id" value="${staff.depart.id}" labelName="depart.name" labelValue="${staff.depart.name}"
								title="部门" url="/sys/office/treeData" extId="${depart.id}"  cssClass="form-control required"  allowClear="true" notAllowSelectParent="true"/>
			</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="岗位：">岗位：</label>
					<sys:gridselect url="${ctx}/personnel/plan/station/data" id="station" name="station.id" value="${staff.station.id}" labelName="station.name" labelValue="${staff.station.name}"
									title="选择岗位" cssClass="form-control required" fieldLabels="岗位" fieldKeys="name" searchLabels="岗位" searchKeys="name" ></sys:gridselect>
				</div>
				<%--<div class="col-xs-12 col-sm-6 col-md-4">--%>
					<%--<label class="label-item single-overflow pull-left" title="学历：">学历：</label>--%>
					<%--<sys:gridselect url="${ctx}/personnel/plan/education/data" id="education" name="education.id" value="${staff.education.id}" labelName="education.educate" labelValue="${staff.education.educate}"--%>
									<%--title="选择学历" cssClass="form-control required" fieldLabels="学历" fieldKeys="educate" searchLabels="学历" searchKeys="educate" ></sys:gridselect>--%>
				<%--</div>--%>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left">学历：</label>

					<form:select path="education" id="getEdu"  class="form-control required">
						<form:option value=""></form:option>
						<c:forEach items="${education}" var="o">
							<form:option value="${o.id}" label="">${o.educate}</form:option>
						</c:forEach>
						<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					</form:select>

				</div>
				<%--<div class="col-xs-12 col-sm-6 col-md-4">--%>
					<%--<label class="label-item single-overflow pull-left" title="员工职级：">员工职级：</label>--%>
					<%--<sys:gridselect url="${ctx}/personnel/plan/rank/data" id="rank" name="rank.id" value="${staff.rank.id}" labelName="rank.rankName" labelValue="${staff.rank.rankName}"--%>
									<%--title="选择员工职级" cssClass="form-control required" fieldLabels="职级" fieldKeys="rankName" searchLabels="职级" searchKeys="rankName" ></sys:gridselect>--%>
				<%--</div>--%>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left">员工职级：</label>

					<form:select path="rank" id="getRank"  class="form-control required">
						<form:option value=""></form:option>
						<c:forEach items="${rank}" var="o">
							<form:option value="${o.id}" label="">${o.rankName}</form:option>
						</c:forEach>
						<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					</form:select>

				</div>
				<%--<div class="col-xs-12 col-sm-6 col-md-4">--%>
					<%--<label class="label-item single-overflow pull-left" title="员工状态：">员工状态：</label>--%>
					<%--<sys:gridselect url="${ctx}/personnel/manage/staffStatus/data" id="status" name="status.id" value="${staff.status.id}" labelName="status.status" labelValue="${staff.status.status}"--%>
									<%--title="选择员工状态" cssClass="form-control required" fieldLabels="状态" fieldKeys="status" searchLabels="状态" searchKeys="status" ></sys:gridselect>--%>
				<%--</div>--%>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left">员工状态：</label>

					<form:select path="status" id="getStatus"  class="form-control required">
						<form:option value=""></form:option>
						<c:forEach items="${status}" var="o">
							<form:option value="${o.id}" label="">${o.status}</form:option>
						</c:forEach>
						<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					</form:select>

				</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="专业：">专业：</label>
					<form:input path="major" htmlEscape="false" maxlength="64"  class=" form-control"/>
				</div>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<shiro:hasPermission name="personnel:manager:staff:add">
				<button id="add" class="btn btn-primary" onclick="add()">
					<i class="glyphicon glyphicon-plus"></i> 新建
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="personnel:manager:staff:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="personnel:manager:staff:del">--%>
				<%--<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">--%>
	            	<%--<i class="glyphicon glyphicon-remove"></i> 删除--%>
	        	<%--</button>--%>
			<%--</shiro:hasPermission>--%>
			<shiro:hasPermission name="personnel:manager:staff:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="personnel:manager:staff:export">
	        		<button id="export" class="btn btn-warning">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			 </shiro:hasPermission>
	                 <shiro:hasPermission name="personnel:manager:staff:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission>
		    </div>
		
	<!-- 表格 -->
	<table id="staffTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="personnel:manager:staff:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="personnel:manager:staff:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="personnel:manager:staff:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>
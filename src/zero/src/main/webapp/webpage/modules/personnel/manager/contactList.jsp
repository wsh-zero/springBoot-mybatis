<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>通讯录管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="contactList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">通讯录列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="contact" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="姓名：">姓名：</label>
				<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="name" name="name.id" value="${contact.name.id}" labelName="name.name" labelValue="${contact.name.name}"
					title="选择姓名" cssClass="form-control required" fieldLabels="部门|名字" fieldKeys="depart.name|name" searchLabels="部门|名字" searchKeys="depart.name|name" ></sys:gridselect>
			</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="所属部门：">所属部门：</label>
					<sys:treeselect id="depart" name="depart.id" value="${contact.depart.id}" labelName="depart.name" labelValue="${contact.depart.name}"
									title="部门" extId="${depart.id}" url="/sys/office/treeData" cssClass="form-control " notAllowSelectParent="false" allowClear="true"/>
				</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="员工编号：">员工编号：</label>
				<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="staffCode" name="staffCode.id" value="${contact.staffCode.id}" labelName="staffCode.code" labelValue="${contact.staffCode.code}"
					title="选择员工编号" cssClass="form-control " fieldLabels="员工编号" fieldKeys="code" searchLabels="员工编号" searchKeys="code" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="岗位：">岗位：</label>
				<sys:gridselect url="${ctx}/personnel/plan/station/data" id="station" name="station.id" value="${contact.station.id}" labelName="station.name" labelValue="${contact.station.name}"
					title="选择岗位" cssClass="form-control " fieldLabels="岗位" fieldKeys="name" searchLabels="岗位" searchKeys="name" ></sys:gridselect>
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
			<shiro:hasPermission name="personnel:manager:contact:add">
				<button id="add" class="btn btn-primary" onclick="add()">
					<i class="glyphicon glyphicon-plus"></i> 新建
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="personnel:manager:contact:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="personnel:manager:contact:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="personnel:manager:contact:import">--%>
				<%--<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>--%>
			<%--</shiro:hasPermission>--%>
			<shiro:hasPermission name="personnel:manager:contact:export">
	        		<button id="export" class="btn btn-warning">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			 </shiro:hasPermission>
	                 <shiro:hasPermission name="personnel:manager:contact:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission>
		    </div>
		
	<!-- 表格 -->
	<table id="contactTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="personnel:manager:contact:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="personnel:manager:contact:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="personnel:manager:contact:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>
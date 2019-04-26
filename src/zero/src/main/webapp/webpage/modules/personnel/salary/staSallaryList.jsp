<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>员工薪资配置管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="staSallaryList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">员工薪资配置列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="staSallary" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="姓名：">姓名：</label>
				<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="name" name="name.id" value="${staSallary.name.id}" labelName="name.name" labelValue="${staSallary.name.name}"
					title="选择姓名" cssClass="form-control " fieldLabels="部门|姓名" fieldKeys="depart.name|name" searchLabels="姓名" searchKeys="name" ></sys:gridselect>
			</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left">员工状态：</label>

						<form:select path="status" id="getStatus"  class="form-control required">
							<form:option value=""></form:option>
							<c:forEach items="${staffStatus}" var="o">
								<form:option value="${o.id}" label="">${o.status}</form:option>
							</c:forEach>
							<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						</form:select>

				</div>
			 <%--<div class="col-xs-12 col-sm-6 col-md-4">--%>
				<%--<label class="label-item single-overflow pull-left" title="员工状态：">员工状态：</label>--%>
				<%--<sys:gridselect url="${ctx}/personnel/manage/staffStatus/data" id="status" name="status.id" value="${staSallary.status.id}" labelName="status.status" labelValue="${staSallary.status.status}"--%>
					<%--title="选择员工状态" cssClass="form-control " fieldLabels="员工状态" fieldKeys="status" searchLabels="员工状态" searchKeys="status" ></sys:gridselect>--%>
			<%--</div>--%>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="所属部门：">所属部门：</label>
				<sys:treeselect id="depart" name="depart.id" value="${staSallary.depart.id}" labelName="depart.name" labelValue="${staSallary.depart.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="form-control" allowClear="true" notAllowSelectParent="flase"/>
			</div>
			 <%--<div class="col-xs-12 col-sm-6 col-md-4">--%>
				<%--<label class="label-item single-overflow pull-left" title="薪资账套：">薪资账套：</label>--%>
				<%--<sys:gridselect url="${ctx}/personnel/salary/saAll/data" id="allocation" name="allocation.id" value="${staSallary.allocation.id}" labelName="allocation.allocation" labelValue="${staSallary.allocation.allocation}"--%>
					<%--title="选择薪资账套" cssClass="form-control " fieldLabels="薪资账套" fieldKeys="allocation" searchLabels="薪资账套" searchKeys="allocation" ></sys:gridselect>--%>
			<%--</div>--%>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left">薪资账套：</label>

						<form:select path="allocation" id="getSa"  class="form-control required">
							<form:option value=""></form:option>
							<c:forEach items="${saAll}" var="o">
								<form:option value="${o.id}" label="">${o.allocation}</form:option>
							</c:forEach>
							<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						</form:select>

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
			<shiro:hasPermission name="personnel:salary:staSallary:add">
				<button id="add" class="btn btn-primary" onclick="add()">
					<i class="glyphicon glyphicon-plus"></i> 新建
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="personnel:salary:staSallary:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="personnel:salary:staSallary:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="personnel:salary:staSallary:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="personnel:salary:staSallary:export">
	        		<button id="export" class="btn btn-warning">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			 </shiro:hasPermission>
	                 <shiro:hasPermission name="personnel:salary:staSallary:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission>
		    </div>
		
	<!-- 表格 -->
	<table id="staSallaryTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="personnel:salary:staSallary:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="personnel:salary:staSallary:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="personnel:salary:staSallary:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>工资条管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="paySlipListJs.jsp" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">工资条列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="paySlip" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="姓名：">姓名：</label>
				<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="name" name="name.id" value="${paySlip.name.id}" labelName="name.name" labelValue="${paySlip.name.name}"
					title="选择姓名" cssClass="form-control " fieldLabels="部门|姓名" fieldKeys="depart.name|name" searchLabels="姓名" searchKeys="name" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="部门：">部门：</label>
				<sys:treeselect id="depart" name="depart.id" value="${paySlip.depart.id}" labelName="depart.name" labelValue="${paySlip.depart.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="发放月：">&nbsp;发放月：</label>
					<div class="col-xs-12">
						<div class='input-group form_datetime' id='releaseTime' >
			                   <input type='text'  name="releaseTime" class="form-control"  pattern="yyyy-MM"/>
			                   <span class="input-group-addon">
			                       <span class="glyphicon glyphicon-calendar"></span>
			                   </span>
			            </div>	
					</div>
				</div>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="工资状态：">工资状态：</label>
				<form:select path="status"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('salary_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
		<c:if test="${isSelf}">
		<div id="toolbar">
			<shiro:hasPermission name="personnel:salary:paySlip:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission>
		</div>
		</c:if>
	<!-- 工具栏 -->
		<c:if test="${!isSelf}">
	<div id="toolbar">
			<%--<shiro:hasPermission name="personnel:salary:paySlip:add">--%>
				<%--<button id="add" class="btn btn-primary" onclick="add()">--%>
					<%--<i class="glyphicon glyphicon-plus"></i> 新建--%>
				<%--</button>--%>
			<%--</shiro:hasPermission>--%>
			<shiro:hasPermission name="personnel:salary:paySlip:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="personnel:salary:paySlip:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="personnel:salary:paySlip:import">--%>
				<%--<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>--%>
			<%--</shiro:hasPermission>--%>
			<shiro:hasPermission name="personnel:salary:paySlip:export">
	        		<button id="export" class="btn btn-warning">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>

			 </shiro:hasPermission>
	                 <shiro:hasPermission name="personnel:salary:paySlip:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission>
		    </div>
		</c:if>
		
	<!-- 表格 -->
	<table id="paySlipTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
		<c:if test="${!isSelf}">
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="personnel:salary:paySlip:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="personnel:salary:paySlip:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="personnel:salary:paySlip:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>
		</c:if>
	</div>
	</div>
	</div>
</body>
</html>
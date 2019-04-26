<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>对象管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {

		});
		function getDepart() {
            var rank=$(this).attr("getRank").value;
             jp.post("${ctx}/personnel/achieve/objConf/getDepart", {rank: rank} ,function (data){

                 let dataList = data.body.list;
                 let elmt = jQuery('#departs-container');
                 let tpl = '<label><input name="departs" type="checkbox" value="@{val}" />@{name}</label>';
                 let view = '';
                 // 渲染数据
                 for (index in dataList) {
                     let dataItem = dataList[index];
                     let itemView = tpl.replace('@{val}', dataItem.id).replace('@{name}', dataItem.name);
                     view += itemView;
                 }
                 elmt.html(view);
			 }

             )

        }
		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/personnel/achieve/objConf/save",$('#inputForm').serialize(),function(data){
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
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="objConf" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>职级名称：</label></td>
					<td class="width-35">
						<form:select path="rank" id="getRank"  onchange="getDepart()" class="form-control required">
							<form:option value=""></form:option>
								<form:option value="a">123</form:option>
						<c:forEach items="${rank}" var="o">
							<form:option value="${o.id}" label="">${o.rankName}</form:option>
						</c:forEach>
							<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						</form:select>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>分组名称：</label></td>
					<td class="width-35">
						<form:input path="group" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门名称：</label></td>
					<td class="width-35" id="departs-container">
						<sys:checkbox id="departs" name="departs" items="${obj}" values="${objConf.departs}" cssClass="i-checks required"/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>

<script>
    // getDepart();
</script>
</body>
</html>
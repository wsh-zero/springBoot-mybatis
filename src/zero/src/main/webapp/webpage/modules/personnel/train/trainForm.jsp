<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>培训管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/personnel/train/train");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

				//富文本初始化
			$('#content').summernote({
				height: 300,                
                lang: 'zh-CN',
                callbacks: {
                    onChange: function(contents, $editable) {
                        $("input[name='content']").val($('#content').summernote('code'));//取富文本的值
                    }
                }
            });
	        $('#startTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm"
		    });
	        $('#endTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm"
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
				<a class="panelButton" href="${ctx}/personnel/train/train${isSelf?'/self':'' }"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="train" action="${ctx}/personnel/train/train/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>主题：</label>
					<div class="col-sm-10">
						<form:input path="title" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>培训类型：</label>
					<div class="col-sm-10">
						<form:select path="type" id="getType"  class="form-control required">
							<form:option value=""></form:option>
							<c:forEach items="${type}" var="o">
								<form:option value="${o.id}" label="">${o.name}</form:option>
							</c:forEach>
							<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">内容：</label>
					<div class="col-sm-10">
                        <input type="hidden" name="content" value=" ${train.content}"/>
						<div id="content">
                          ${fns:unescapeHtml(train.content)}
                        </div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>发布人：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/personnel/manager/staff/data" id="initiator" name="initiator.id" value="${train.initiator.id}" labelName="initiator.name" labelValue="${train.initiator.name}"
							 title="选择培训发起人" cssClass="form-control required" fieldLabels="部门|员工姓名" fieldKeys="depart.name|name" searchLabels="员工姓名" searchKeys="name" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>培训开始时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='startTime'>
							<input type='text'  name="startTime" class="form-control required"  value="<fmt:formatDate value="${train.startTime}" pattern="yyyy-MM-dd HH:mm"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>培训结束时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='endTime'>
							<input type='text'  name="endTime" class="form-control required"  value="<fmt:formatDate value="${train.endTime}" pattern="yyyy-MM-dd HH:mm"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>培训地点：</label>
				<div class="col-sm-10">
					<form:input path="venue" htmlEscape="false"    class="form-control required"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>培训费用：</label>
				<div class="col-sm-10">
					<form:input path="cost" htmlEscape="false"    class="form-control required"/>
				</div>
			</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">附件：</label>
					<div class="col-sm-10">
						<c:if test="${oaNotify.status ne '1'}">
						<sys:fileUpload path="file"  value="${train.file}" type="file" uploadPath="/personnel/train/train"/>
						</c:if>
						<c:if test="${oaNotify.status eq '1'}">
							<sys:fileUpload path="file"  value="${train.file}" type="file" uploadPath="/personnel/train/train" readonly="true"/>
						</c:if>
					</div>
				</div>
			<c:if test="${train.status ne '1'}">
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>状态：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>接受人：</label>
					<div class="col-sm-10">
						<sys:userselect id="trainRecord" name="trainRecordIds" value="${train.trainRecordIds}" labelName="trainRecordNames" labelValue="${train.trainRecordNames}"
										cssClass="form-control required"  isMultiSelected="true"/>
					</div>
				</div>
			</c:if>


			<c:if test="${train.status eq '1'}">
				<div class="form-group">
					<table>
						<tr>
							<td  class="width-15 active">	<label class="pull-right">接受人：</label></td>
							<td class="width-35" colspan="3"><table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
								<thead>
								<tr>
									<th>接受人</th>
									<th>接受部门</th>
									<th>阅读状态</th>
									<th>阅读时间</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${train.trainRecordList}" var="trainRecord">
									<tr>
										<td>
												${trainRecord.user.name}
										</td>
										<td>
												${trainRecord.user.office.name}
										</td>
										<td>
												${fns:getDictLabel(trainRecord.readFlag, 'oa_notify_read', '')}
										</td>
										<td>
											<fmt:formatDate value="${trainRecord.readDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
								已查阅：${train.readNum} &nbsp; 未查阅：${train.unReadNum} &nbsp; 总共：${train.readNum + train.unReadNum}</td>
						</tr>
					</table>
				</div>
			</c:if>
			<c:if test="${train.status ne '1'}">
			<shiro:hasPermission name="personnel:train:train:edit">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
			</shiro:hasPermission>
			</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>
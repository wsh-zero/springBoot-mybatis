<%--<%@ page contentType="text/html;charset=UTF-8" %>--%>
<%--<%@ include file="/webpage/include/taglib.jsp"%>--%>
<%--<html>--%>
<%--<head>--%>
    <%--<title>员工信息管理</title>--%>
    <%--<meta name="decorator" content="ani"/>--%>
    <%--<script type="text/javascript">--%>

        <%--$(document).ready(function() {--%>
            <%--jp.ajaxForm("#inputForm",function(data){--%>
                <%--if(data.success){--%>
                    <%--jp.success(data.msg);--%>
                    <%--jp.go("${ctx}/personnel/manager/staff");--%>
                <%--}else{--%>
                    <%--jp.error(data.msg);--%>
                    <%--$("#inputForm").find("button:submit").button("reset");--%>
                <%--}--%>
            <%--});--%>

            <%--$('#birthDate').datetimepicker({--%>
                <%--format: "YYYY-MM-DD HH:mm:ss"--%>
            <%--});--%>
            <%--$('#entryDate').datetimepicker({--%>
                <%--format: "YYYY-MM-DD HH:mm:ss"--%>
            <%--});--%>
        <%--});--%>
    <%--</script>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="wrapper wrapper-content">--%>
    <%--<div class="row">--%>
        <%--<div class="col-md-12">--%>
            <%--<div class="panel panel-primary">--%>
                <%--<div class="panel-heading">--%>
                    <%--<h3 class="panel-title">--%>
                        <%--<a class="panelButton" href="${ctx}/personnel/manager/contractInfo"><i class="ti-angle-left"></i> 返回</a>--%>
                    <%--</h3>--%>
                <%--</div>--%>
                <%--<div class="panel-body">--%>
                    <%--<form:form id="inputForm" modelAttribute="staff" action="${ctx}/personnel/manager/contractInfo/save" method="post" class="form-horizontal">--%>
                        <%--<form:hidden path="id"/>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label"><font color="red">*</font>员工姓名：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:input path="name" htmlEscape="false"    class="form-control required"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">合同类型：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<sys:gridselect url="${ctx}/personnel/manage/contractType/data" id="contractType" name="contractType.id" value="${staff.contractType.id}" labelName="contractType.name" labelValue="${staff.contractType.name}"--%>
                                                <%--title="选择合同类型" cssClass="form-control " fieldLabels="合同类型" fieldKeys="name" searchLabels="" searchKeys="" ></sys:gridselect>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">合同编号：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:input path="contractCode" htmlEscape="false"    class="form-control "/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">试用期到期日：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:input path="trailEnd" htmlEscape="false"    class="form-control "/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">合同开始日期：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:input path="contractStart" htmlEscape="false"    class="form-control "/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">合同结束日期：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:input path="ContractEnd" htmlEscape="false"    class="form-control "/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">保密协议编号：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:input path="secretCode" htmlEscape="false"    class="form-control "/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">协议起始日：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:input path="secretStart" htmlEscape="false"    class="form-control "/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">协议终止日：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:input path="secretEnd" htmlEscape="false"    class="form-control "/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">签订次数：</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:input path="signNumber" htmlEscape="false"    class="form-control "/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<c:if test="${mode == 'add' || mode=='edit'}">--%>
                            <%--<div class="col-lg-3"></div>--%>
                            <%--<div class="col-lg-6">--%>
                                <%--<div class="form-group text-center">--%>
                                    <%--<div>--%>
                                        <%--<button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</c:if>--%>
                    <%--</form:form>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
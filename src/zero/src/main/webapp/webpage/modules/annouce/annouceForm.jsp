<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>发布公告管理</title>
    <meta name="decorator" content="ani"/>
    <%@include file="/webpage/include/summernote.jsp" %>
    <script type="text/javascript">

        $(document).ready(function () {


            //富文本初始化
            $('#content').summernote({
                height: 300,
                lang: 'zh-CN',
                callbacks: {
                    onChange: function (contents, $editable) {
                        $("input[name='content']").val($('#content').summernote('code'));//取富文本的值
                    }
                }
            });
            $('#content').summernote('code', $("input[name='content']").val());

            jp.ajaxForm("#inputForm", function (data) {
                if (data.success) {
                    jp.success(data.msg);
                    jp.go("${ctx}/annouce/annouce");
                } else {
                    jp.error(data.msg);
                    $("#inputForm").find("button:submit").button("reset");
                }
            });

            $('#startTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
            $('#endTime').datetimepicker({
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
                        <a class="panelButton" href="${ctx}/annouce/annouce${isSelf?'/self':'' }"><i
                                class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="annouce" action="${ctx}/annouce/annouce/save"
                               method="post" class="form-horizontal">
                        <form:hidden path="id"/>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>标题：</label>
                            <div class="col-sm-10">
                                <form:input path="title" htmlEscape="false" class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>内容：</label>
                            <div class="col-sm-10">
                                    <%--<form:textarea path="content" htmlEscape="false" rows="20"   class="form-control required"/>--%>
                                    <%--<input type="hidden" name="content" value=" ${annouce.content}"/>
                                    <div id="contentid">
                                            ${fns:unescapeHtml(annouce.content)}
                                    </div>--%>
                                <input type="hidden" name="content" value="${annouce.content}"/>
                                <div id="content">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">附件：</label>
                            <div class="col-sm-10">
                                <sys:fileUpload path="appendix" value="${annouce.appendix}" type="file"
                                                uploadPath="/annouce/annouce"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>开始日期：</label>
                            <div class="col-sm-10">
                                <div class='input-group form_datetime' id='startTime'>
                                    <input type='text' name="startTime" class="form-control required"
                                           value="<fmt:formatDate value="${annouce.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                    <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>结束日期：</label>
                            <div class="col-sm-10">
                                <div class='input-group form_datetime' id='endTime'>
                                    <input type='text' name="endTime" class="form-control required"
                                           value="<fmt:formatDate value="${annouce.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                    <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>类型：</label>
                            <div class="col-sm-10">
                                <form:select path="type" class="form-control required">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('annoucetype')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>
                        </div>
                        <c:if test="${annouce.status ne '1'}">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>接收人：</label>
                                <div class="col-sm-10">
                                    <sys:userselect id="receivePerson" name="receivePersons"
                                                    value="${annouce.receivePersons}" labelName="annouceRecordNames"
                                                    labelValue="${annouce.annouceRecordNames}"
                                                    cssClass="form-control required" isMultiSelected="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>状态：</label>
                                <div class="col-sm-10">
                                    <form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}"
                                                       itemLabel="label" itemValue="value" htmlEscape="false"
                                                       class="i-checks required"/>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${annouce.status eq '1'}">
                            <div class="form-group">
                                <table>
                                    <tr>
                                        <td class="width-15 active"><label class="pull-right">接受人：</label></td>
                                        <td class="width-35" colspan="3">
                                            <table id="contentTable"
                                                   class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                                                <thead>
                                                <tr>
                                                    <th>接受人</th>
                                                    <th>接受部门</th>
                                                    <th>阅读状态</th>
                                                    <th>阅读时间</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${annouce.annouceRecordList}" var="annouceRecord">
                                                    <tr>
                                                        <td>
                                                                ${annouceRecord.user.name}
                                                        </td>
                                                        <td>
                                                                ${annouceRecord.user.office.name}
                                                        </td>
                                                        <td>
                                                                ${fns:getDictLabel(annouceRecord.readFlag, 'oa_notify_read', '')}
                                                        </td>
                                                        <td>
                                                            <fmt:formatDate value="${annouceRecord.readDate}"
                                                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            已查阅：${annouce.readNum} &nbsp; 未查阅：${annouce.unReadNum} &nbsp;
                                            总共：${annouce.readNum + annouce.unReadNum}</td>
                                    </tr>
                                </table>
                            </div>
                        </c:if>
                        <c:if test="${annouce.status ne '1'}">
                            <shiro:hasPermission name="annouce:annouce:edit">
                                <div class="col-lg-3"></div>
                                <div class="col-lg-6">
                                    <div class="form-group text-center">
                                        <div>
                                            <button class="btn btn-primary btn-block btn-lg btn-parsley"
                                                    data-loading-text="正在提交...">提 交
                                            </button>
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
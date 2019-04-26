<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>简历管理管理</title>
    <meta name="decorator" content="ani"/>
    <style>
        hr {
            margin-top: 5px;
            margin-bottom: 5px;
        }
        .htitle {
            color: #3ca2e0;
        }
    </style>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a class="panelButton" href="${ctx}/resumeinfo/resumeInfo"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <a type="button" class="btn btn-primary" href="${ctx}/resumeinfo/resumeInfo/resumecontent?id=${resumeInfo.id}" target="_blank">查看原简历</a>
                    <h4 class="htitle">●基本信息</h4>
                    <hr/>
                    <img border="0" class="portrait" height="122"
                         src="http://mypics.zhaopin.cn/avatar/2019/3/9/69571add-3282-4561-8b54-fd79183d2088.jpg"
                         style="vertical-align:middle" width="100">
                    <div style="display: inline-block; vertical-align: bottom; margin-left: 10px;">
                        <div>
                            <span><b>姓名：</b>${resumeInfo.name}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>性别：</b>${resumeInfo.sex}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>联系电话：</b>${resumeInfo.mobile}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>邮箱：</b>${resumeInfo.email}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                        </div>
                        <div>
                            <span><b>学历：</b>${resumeInfo.eduBack}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>工作经验：</b>${resumeInfo.workYears}年</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>出生年月：</b>${resumeInfo.birth}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>现居地：</b>${resumeInfo.residence}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>户籍地：</b>${resumeInfo.domicile}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                        </div>
                        <div style="max-width: 1100px; margin-top: 8px;">
                            <b>自我评价：</b>${resumeInfo.selfEval}
                        </div>
                    </div>
                    <h4 style="margin-top: 18px;" class="htitle">●求职意向</h4>
                    <hr/>
                    <div>
                        <div>
                            <span><b>期望工作地区：</b>${resumeInfo.jobIntension.workPlace}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>期望工作性质：</b>${resumeInfo.jobIntension.workNature}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>期望从事职业：</b>${resumeInfo.jobIntension.job}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>期望月薪：</b>${resumeInfo.jobIntension.minSalary}-
                                ${resumeInfo.jobIntension.maxSalary}${resumeInfo.jobIntension.unit}
                            </span>&nbsp;&nbsp;&nbsp;&nbsp;
                        </div>
                        <div>
                            <span><b>目前状况：</b>${resumeInfo.jobIntension.curStatus}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span><b>期望从事行业：</b>${resumeInfo.jobIntension.expectIndustry}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                        </div>
                    </div>
                    <h4 style="margin-top: 18px;" class="htitle">●工作经历</h4>
                    <div>
                        <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <td><b>公司名称</b></td>
                                    <td><b>入职时间</b></td>
                                    <td><b>离职时间</b></td>
                                    <td><b>岗位名称</b></td>
                                    <td><b>薪资水平</b></td>
                                    <td><b>行业类型</b></td>
                                    <td><b>工作描述</b></td>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${resumeInfo.workExps}" var="exp">
                                <tr>
                                    <td>${exp.company}</td>
                                    <td>${exp.startDate}</td>
                                    <td>${exp.endDate}</td>
                                    <td>${exp.post}</td>
                                    <td>${exp.salary}${exp.unit}</td>
                                    <td>${exp.industryType}</td>
                                    <td>${exp.describe}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        </div>
                    </div>
                    <h4 style="margin-top: 18px;" class="htitle">●项目经验</h4>
                    <div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <td><b>项目名称</b></td>
                                <td><b>项目介绍</b></td>
                                <td><b>经历时间</b></td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${resumeInfo.projectExps}" var="exp">
                                <tr>
                                    <td>${exp.name}</td>
                                    <td>${exp.intro}</td>
                                    <td>${exp.time}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <h4 style="margin-top: 18px;" class="htitle">●教育经历</h4>
                    <div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <td><b>时间</b></td>
                                <td><b>学校名称</b></td>
                                <td><b>专业</b></td>
                                <td><b>学位</b></td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${resumeInfo.eduBackgrounds}" var="edu">
                                <tr>
                                    <td>${edu.time}</td>
                                    <td>${edu.school}</td>
                                    <td>${edu.major}</td>
                                    <td>${edu.degree}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
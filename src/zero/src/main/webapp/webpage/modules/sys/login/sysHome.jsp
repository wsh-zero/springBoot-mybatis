<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>

<head>
	<title>首页</title>
	<meta name="decorator" content="ani"/>
	<link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	<style>

		#body-container {
			margin-left: 0px !important;
			/**padding: 10px;*/
			margin-top: 0px !important;
			overflow-x: hidden!important;
			transition: all 0.2s ease-in-out !important;
			height: 100% !important;
		}
		.apply{
			border-radius: 10px;
		}
		.apply .stat{
			padding: 20px;
			color: rgb(247,156,66);
			border-radius: 10px;
		}
		.apply .have{
			color: rgb(74,204,153);
		}
		.apply .backlog{
			color: rgb(94,154,251);
		}
		.apply .examine{
			color: rgb(255,116,116);
		}
		.apply a.stat .stat-label{
			padding-left: 60px;
			width: 160px;
		}
		.apply a.stat .stat-label h3:last-child{
			margin-left: 20px;
			font-weight: bold;
		}
		.apply a.stat .stat-icon{

		}
		.annunciate{
			/*background-color: red;*/
			width: 100%;
			height: 200px;
			border-radius: 10px;
		}
		.annunciate .briefcase{
			background-color: #fff;
			min-height: 300px;
			margin-bottom: 10px;
			border-top: 3px solid #3CA2E0;
			border-radius:0  0 10px 10px;

		}
		.annunciate .briefcase .official_title{
			display: flex;
			justify-content: space-between;
		}
		.annunciate .briefcase .official_title .official{
			padding-top: 10px;
			padding-left: 10px;
			font-size: 16px;
			color: #3CA2E0;
		}
		.annunciate .announcement{
			background-color: #fff;
			min-height: 300px;
			margin-bottom: 10px;
			border-top: 3px solid #3CA2E0;
			border-radius:0 0 10px 10px;
		}
		#myTabContent1{
			padding: 10px;
			max-height: 260px;
			overflow-y: auto;
		}
		#myTabContent2{
			padding: 10px;
			max-height: 260px;
			overflow-y: auto;
		}
		.annunciate #myTab1{

		}
		.annunciate .speedy{
			padding: 10px;
			min-height: 260px;
			overflow-y: auto;
			background-color: #fff;
			border-top: 3px solid #3CA2E0;
			border-radius:0 0 10px 10px;
		}
		.annunciate .speedy .speedy_title{
			width: 100%;
			font-size: 14px;
			color: #3CA2E0;
			margin-bottom: 10px;
		}
		.annunciate .speedy .speedy_button li{
			width: 144px;
			height: 144px;
			margin-right: 40px;
			display: flex;
			justify-content: center;
			align-items: center;
			cursor: pointer;
		}
		.annunciate .speedy .speedy_button li a{
			display: block;
			width: 100%;
			height: 100%;
			text-align: center;
			align-items: center;
			align-content: center;
			padding-top: 28px;
		}
		.annunciate .speedy .speedy_button li:hover{
			background-color: #ECF0F1;
		}
	</style>
</head>
<body class="">
<div id="body-container" class="wrapper wrapper-content">
	<div class="conter-wrapper home-container">
		<%--上方按钮--%>
		<div class="row home-row apply">
			<%--我的申请--%>
			<div class="col-md-4 col-lg-3">
				<a href="#" class="stat hvr-wobble-horizontal">
					<div class=" stat-label">
						<h3>我的申请</h3>
						<h3>2</h3>
					</div>
					<div class=" stat-icon">
						<i class="fa fa-calendar-plus-o fa-5x"></i>
						<%--<img src="../../../../act/rest/img/申请.png" alt="">--%>
					</div>
				</a>
			</div>
			<%--我的已办--%>
			<div class="col-md-4 col-lg-3">
				<a href="#" class="stat have hvr-wobble-horizontal">
					<div class=" stat-label">
						<h3>我的已办</h3>
						<h3>2</h3>
					</div>
					<div class="stat-icon">
						<i class="fa fa-calendar-check-o fa-5x "></i>
					</div>
				</a>
			</div>
			<%--我的待办--%>
			<div class="col-md-4 col-lg-3">
				<a href="#" class="stat backlog hvr-wobble-horizontal">
					<div class=" stat-label">
						<h3>我的待办</h3>
						<h3>2</h3>
					</div>
					<div class=" stat-icon">
						<i class="fa fa-calendar-minus-o fa-5x"></i>
					</div>
				</a>
			</div>
			<%--审批超时--%>
			<div class="col-md-4 col-lg-3">
				<a href="#" class="stat examine  hvr-wobble-horizontal">
					<div class=" stat-label">
						<h3>审批超时</h3>
						<h3>2</h3>
					</div>
					<div class=" stat-icon">
						<i class="fa fa-calendar-times-o fa-5x "></i>
					</div>
				</a>
			</div>
		</div>
		<%--下方通告--%>
		<div class="row home-row annunciate">
			<%--左侧公司通告--%>
			<div class="col-lg-6 col-md-6">
				<div class="home-stats">
					<%--公司公文--%>
					<div class="briefcase">
						<div class="official_title">
							<div class="official"><i class="fa fa-volume-up fa-1x "></i>&nbsp; 公司公文</div>
							<ul id="myTab1" class="nav nav-tabs" role="tablist">

								<li class="active"><a href="#unread" role="tab" data-toggle="tab">未读</a></li>
								<li><a href="#read" role="tab" data-toggle="tab">已读</a></li>
								<li><a href="#" role="tab" data-toggle="tab">更多</a></li>
							</ul>
						</div>
						<!-- 选项卡面板 -->
						<div id="myTabContent1" class="tab-content">
							<div class="tab-pane active" id="unread">
								<table class="table table-hover">
									<tr><td>未读内容</td></tr>
									<tr><td>未读内容</td></tr>
									<tr><td>未读内容</td></tr>
									<tr><td>未读内容</td></tr>
									<tr><td>未读内容</td></tr>
									<tr><td>未读内容</td></tr>
									<tr><td>未读内容</td></tr>
								</table>
							</div>
							<div class="tab-pane" id="read">
								<table class="table table-hover">
									<tr><td>已读内容</td></tr>
									<tr><td>内容</td></tr>
									<tr><td>内容</td></tr>
									<tr><td>内容</td></tr>
									<tr><td>内容</td></tr>
									<tr><td>内容</td></tr>
									<tr><td>内容</td></tr>
								</table>
							</div>
						</div>
					</div>
					<%--公告面板--%>
					<div class="announcement">
						<ul id="myTab2" class="nav nav-tabs" role="tablist">
							<li class="active"><a href="#bulletin" role="tab" data-toggle="tab">公告</a></li>
							<li><a href="#rule" role="tab" data-toggle="tab">会议摘要</a></li>
							<li><a href="#train" role="tab" data-toggle="tab">培训信息</a></li>
						</ul>
						<!-- 选项卡面板 -->
						<div id="myTabContent2" class="tab-content">
							<div class="tab-pane active" id="bulletin">
								<table class="table table-hover">
									<tr><td>公告内容</td></tr>
									<tr><td>公告内容</td></tr>
									<tr><td>公告内容</td></tr>
									<tr><td>公告内容</td></tr>
									<tr><td>公告内容</td></tr>
									<tr><td>公告内容</td></tr>
									<tr><td>公告内容</td></tr>
								</table>
							</div>
							<div class="tab-pane" id="rule">会议内容</div>
							<div class="tab-pane" id="train">培训内容</div>
						</div>
					</div>
				</div>
			</div>
			<%--便捷操作按钮面板--%>
			<div class="col-lg-6 col-md-6">
				<div class="speedy">
					<div class="speedy_title">
						<h4><i class="fa fa-th fa-1x "></i>&nbsp;便捷操作</h4>
					</div>
					<div>
						<ul class="speedy_button">
							<li class="col-lg-3 col-md-6">
								<a href="#"><i class="fa fa-cloud-upload fa-4x text-info "></i><br>
									请假审批
								</a>
							</li>
							<li class="col-lg-3 col-md-6">
								<a href="#"><i class="fa fa-cloud-upload fa-4x text-info "></i><br>
									请假审批
								</a>
							</li>
							<li class="col-lg-3 col-md-6">
								<a href="#"><i class="fa fa-cloud-upload fa-4x text-info "></i><br>
									请假审批
								</a>
							</li>
							<li class="col-lg-3 col-md-6">
								<a href="#"><i class="fa fa-cloud-upload fa-4x text-info "></i><br>
									请假审批
								</a>
							</li>
							<li class="col-lg-3 col-md-6">
								<a href="#"><i class="fa fa-cloud-upload fa-4x text-info "></i><br>
									请假审批
								</a>
							</li>
							<li class="col-lg-3 col-md-6">
								<a href="#"><i class="fa fa-cloud-upload fa-4x text-info "></i><br>
									请假审批
								</a>
							</li>
							<li class="col-lg-3 col-md-6">
								<a href="#"><i class="fa fa-cloud-upload fa-4x text-info "></i><br>
									请假审批
								</a>
							</li>
							<li class="col-lg-3 col-md-6">
								<a href="#"><i class="fa fa-cloud-upload fa-4x text-info "></i><br>
									请假审批
								</a>
							</li>
							<li class="col-lg-3 col-md-6">
								<a href="#"><i class="fa fa-cloud-upload fa-4x text-info "></i><br>
									请假审批
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>

</script>


</body>
</html>
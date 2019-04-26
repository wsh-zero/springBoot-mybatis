<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!-- _login_page_ --><!--登录超时标记 勿删-->
<html>

<head>
	<%--<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">--%>
	<meta name="decorator" content="ani"/>
	<%--<title>${fns:getConfig('productName')} 登录</title>--%>
	<title>蜀通OM管理系统 登录</title>
	<script>
		if (window.top !== window.self) {
			window.top.location = window.location;
		}
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				}
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>

</head>


<body>
<div id="loginBox">
	<%--上半部分--%>
	<div id="login">
		<%--logo--%>
		<div id="logo">
			<%--图片--%>
			<div class="login_img">
				<%--<img style="width: 125px;height: 125px;border-radius: 50%" src="${ctxStatic}/common/images/flat-avatar.png">--%>
			</div>
			<%--文字--%>
			<%--<div class="login_text">--%>
			<%--蜀通OM系统--%>
			<%--</div>--%>
		</div>
		<%--登陆表单--%>
		<div id="login_form">
			<h3 class="form_title">用户登录/USER LOGIN</h3>
			<sys:message content="${message}" showType="1"/>
			<form id="loginForm" role="form" action="${ctx}/login" method="post">
				<div class="form-content">
					<div class="form-group">
						<input type="text" id="username" name="username" class="form-control input-underline input-lg required"   placeholder="用户名">
					</div>

					<div class="form-group">
						<input type="password" id="password" name="password"  class="form-control input-underline input-lg required" placeholder="密码">
					</div>
					<c:if test="${isValidateCodeLogin}">
						<div class="form-group  text-muted" style="margin-left: 18px">
							<label class="inline"><font color="black">验证码:</font></label>
							<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;" buttonCssStyle="color:black"/>
						</div>
					</c:if>

					<label style="margin-left: 302px;" class="inline">
						<input  type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} class="ace" />
						<span class="lbl"> 记住我</span>
					</label>
				</div>
				<%--登陆按钮--%>
				<div id="loginBtn">
					<input type="submit" class="btn btn-white"value="登录">
					<%--&nbsp;--%>
					<%--<a style="margin-left: 80px;" href="${ctx}/sys/register" class="btn btn-white btn-outline btn-lg btn-rounded">注册</a>--%>
				</div>

			</form>
		</div>

	</div>
	<%--下半部分--%>
	<div id="login_AQ">
		<h3 style="font-size: 14px;">COPYRIGHT (©) 2019 四川省邻水县雪亮工程后台管理系统</h3>
		<p>备案号：蜀ICP备16004495号</p>
	</div>
</div>
<script>


	$(function(){
		$('.theme-picker').click(function() {
			changeTheme($(this).attr('data-theme'));
		});

	});

	function changeTheme(theme) {
		$('<link>')
				.appendTo('head')
				.attr({type : 'text/css', rel : 'stylesheet'})
				.attr('href', '${ctxStatic}/common/css/app-'+theme+'.css');
		//$.get('api/change-theme?theme='+theme);
		$.get('${pageContext.request.contextPath}/theme/'+theme+'?url='+window.top.location.href,function(result){  });
	}
</script>
<style>
	<%--自己写的样式--%>
	#loginBox{
		width: 100%;
		height: 100%;
	}
	#loginBox #login{
		width: 100%;
		height: 88%;
		background-image: url("${ctxStatic}/common/images/loginbackground.png");
		background-repeat: no-repeat;
		background-size: 100% 100%;
	}
	#loginBox #login #logo{
		width: 430px;
		display: flex;
		justify-content: flex-start;
		position: fixed;
		top: 100px;
		left: 300px;
	}
	#loginBox #login #login_form{
		width: 430px;
		height: 324px;
		border: 1px solid #ccc;
		position: absolute;
		right: 300px;
		top: 240px;
		padding: 20px;
		box-shadow: 0 0 15px rgba(0, 0, 0, 0.5);
		color: #000000;
		background-color: #fff;
	}
	#loginBox #login #login_form #loginForm .form-content .form-group input::-webkit-input-placeholder{
		color: #CCCCCC;
		font-size: 14px;
	}
	#loginBox #login #login_form #loginForm .form-content .form-group .input-underline{
		border-bottom: 1px solid rgba(0, 0, 0, 0.2);

	}
	#loginBox #login #login_form #loginForm .form-content .form-group #username{
		color: #000;
		padding-bottom: 0px;

	}
	#loginBox #login #login_form #loginForm .form-content .form-group #password{
		color: #000;
		padding-bottom: 0px;

	}
	#loginBox #login #login_form .form_title{
		color: #fff;
		font-size: 26px;
		margin-bottom: 20px;
		margin-left: 10px;
		margin-top: 10px;
		font-weight: bold;
		color: #000;
	}

	#loginBox #login #login_form #loginBtn{
		/*margin-left: 40px;*/
		margin-top: 20px;
		width: 100%;
		background-color: #478FE3;
		border-radius: 10px;
	}
	#loginBox #login #login_form #loginBtn .btn{

		width: 100%;
		background-color: #478FE3;
		color: #ccc;
		border-radius: 10px;

	}

	#loginBox #login #login_form #loginBtn .btn:hover{
		color: #fff;
		/*background-color: #478FE3;*/

		/*box-shadow: 0 0 0 1px #188AE2 inset;*/
	}
	#loginBox #login #logo .login_text{
		font-size: 48px;
		margin-left: 20px;
		color: #fff;
		line-height: 125px;

	}

	#loginBox #login_AQ{
		width: 100%;
		height: 12%;
		background-color: #e2e2e2;
		text-align: center;
		color: #ccc;
		padding-top: 20px;
		line-height: 30px;
	}
	/*两个按钮*/
	.btn-outline.btn-white{
		color: #000;
		/*box-shadow:0 0 0 1px rgba(0, 0, 0, 0.2) inset;*/

	}



	li.color-picker i {
		font-size: 24px;
		line-height: 30px;
	}
	.red-base {
		color: #D24D57;
	}
	.blue-base {
		color: #3CA2E0;
	}
	.green-base {
		color: #27ae60;
	}
	.purple-base {
		color: #957BBD;
	}
	.midnight-blue-base {
		color: #2c3e50;
	}
	.lynch-base {
		color: #6C7A89;
	}
</style>
</body>
</html>
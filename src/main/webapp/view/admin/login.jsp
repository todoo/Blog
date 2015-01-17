<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>  
<html lang="zh-CN">  
<head>  
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="<c:url value='/view/lib/bootstrap/css/bootstrap.min.css'/>" />
    <link rel="stylesheet" href="<c:url value='/view/lib/font-awesome/css/font-awesome.min.css'/>" />
    <link rel="stylesheet" href="<c:url value='/view/admin/css/samples.css'/>" />
    <script src="<c:url value='/view/lib/jquery/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/view/lib/bootstrap/js/bootstrap.min.js'/>" type="text/javascript"></script>
	<title>Login|Blog</title>  
</head>  
<body id="login-body" class="my-bg-black-1">  
	<div class="container">
		<div class="col-md-4 col-md-offset-4 jumbotron " id="login-window">
			<form action="${pageContext['request'].contextPath}/j_spring_security_check" method="post">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
	      				<input class="form-control" type="text" name="j_username" placeholder="输入用户名" value='${sessionScope.SPRING_SECURITY_LAST_USERNAME}'>
	      			</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></div>
	      				<input class="form-control" type="password" name="j_password" placeholder="输入密码" value='${sessionScope.SPRING_SECURITY_LAST_USERNAME}'>
      				</div>
				</div>
				<div class="checkbox">
				    <label>
				      <input type="checkbox" name="_spring_security_remember_me">两周之内不用登陆
				    </label>
				  </div>
				<button type="submit" name="submit" class="btn btn-primary" id="login-submit-btn">登陆</button>
				<!-- <button type="reset" name="reset" class="btn btn-warning">重置</button> -->
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			
			/*设置登陆框的垂直位置居于浏览器的垂直中间*/
			setLoginDivMarginTop();
			
			$(window).resize(function(){
				setLoginDivMarginTop();
			});
			
			function setLoginDivMarginTop()
			{
				var browerHeight = $(window).height();
				var loginDivHeight = $("#login-window").height();
				var loginDivPadding = parseInt($("#login-window").css("padding-top"))*2;
				loginDivHeight = loginDivHeight + loginDivPadding;
				var slideHeight = (browerHeight-loginDivHeight)/2;
				$("#login-window").css("margin-top",slideHeight);
			}
			
			/*设置登陆出错时的弹出框*/
			var isLoginError = ${param.error} + "";
			if(isLoginError){
				$("#login-submit-btn").popover({
					title:'',
					content:'<label class="text-danger bg-danger">用户名或密码不正确，请核对后重新输入！</label>',
					trigger:'focus',
					placement:'right',
					html:true
				});
				$("#login-submit-btn").focus();
				$("#login-submit-btn").popover('show');
				$('#login-submit-btn').on('hidden.bs.popover', function () {
					$('#login-submit-btn').popover('destroy')
				});
			}
		});
	</script>
</body>  
</html> 
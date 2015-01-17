<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!DOCTYPE html>
<html>
    <head>
        <title>Blog</title>
        
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="<c:url value='/view/lib/bootstrap/css/bootstrap.min.css'/>" />
        <link rel="stylesheet" href="<c:url value='/view/lib/font-awesome/css/font-awesome.min.css'/>" />
        <link rel="stylesheet" href="<c:url value='/view/lib/durandal/css/durandal.css'/>" />
        <link rel="stylesheet" href="<c:url value='/view/client/css/samples.css'/>" />
    </head>
    <body class="my-bg-black-3 my-fg-white-c">
        <div id="applicationHost">
			 <div id="splash" class="container-fluid">
				<div class="row" style="height:100%;">
					<div class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 my-bg-black-1" style="height:100%;">
						<div class="col-lg-2 col-lg-offset-0 col-md-3 col-md-offset-0 col-sm-2 col-sm-offset-5 col-xs-4 col-xs-offset-4">
							<img src="<c:url value='/resources'/>/${user.logo }" alt="logo" class="img-circle img-responsive" style="margin-top:20px;box-shadow: 3px 3px 5px #888888;">
						</div>
						<div class="col-lg-6 col-lg-offset-1 col-md-6 col-md-offset-1 col-sm-12 col-xs-12">
							<div class="row">
								<h2 class="hidden-sm hidden-xs">${blog.title}</h2>
								<h2 class="text-center hidden-lg hidden-md">${blog.title}</h2>
							</div>
							<div class="row">
								<p class="hidden-sm hidden-xs"><i>${blog.message }</i></p>
								<p class="text-center hidden-lg hidden-md"><i>${blog.message }</i></p>
							</div>
						</div>
						<div class="col-lg-3 col-md-2">
							<c:if test="${user!=null and user.indexAudio!=null and user.indexAudio!=''}">
								<audio autoplay="autoplay" loop="loop" >
								  <source src="<c:url value='/resources'/>/${user.indexAudio}" type="audio/mpeg">
									Your browser does not support the audio element.
								</audio>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script>
			var height = window.innerHeight;
			document.getElementById("splash").style.height=height+"px";
			var ROOT_URL = "${pageContext.request.contextPath}"+"/";
			var MEDIA_ROOT_URL = ROOT_URL + "resources/";
			var USER_URL_ID = "${user.urlID}";
		</script>
        <script src="<c:url value='/view/lib/require/require.js'/>" data-main="<c:url value='/view/client/app/main.js'/>"></script>
    </body>
</html>
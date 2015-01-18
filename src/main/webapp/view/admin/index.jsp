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
        <link rel="stylesheet" href="<c:url value='/view/admin/css/samples.css'/>" />
    </head>
    <body class="my-bg-black-3 my-fg-white-c">
        <div id="applicationHost">
			 
		</div>
		<script>
			var ROOT_URL = "${pageContext.request.contextPath}"+"/";
			var MEDIA_ROOT_URL = ROOT_URL + "resources/";
			window.UEDITOR_HOME_URL = ROOT_URL + "view/lib/ueditor/";
		</script>
        <script src="<c:url value='/view/lib/require/require.js'/>" data-main="<c:url value='/view/admin/app/main.js'/>"></script>
    </body>
</html>
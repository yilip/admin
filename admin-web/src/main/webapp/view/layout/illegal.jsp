<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/style.css"/>
<script type="text/javascript" src="<%=path%>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/user/login.js"></script>
<title>后台非法访问</title>
</head>
<body class="loginbody">
	<center style="font-size:10px;color:red;margin-top:50px">You have no permission to access this page!</center>
</body>
</html>
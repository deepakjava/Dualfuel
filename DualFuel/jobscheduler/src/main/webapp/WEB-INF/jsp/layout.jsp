<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script src="http://code.jquery.com/jquery.js"></script>
<script src="${contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${contextPath}/bootstrap/js/jquery.validate.js"></script>

<link href="${contextPath}/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">

<style type="text/css">
body {
	padding-top: 20px;
	padding-bottom: 40px;
}

/* Custom container */
.container-narrow {
	margin: 0 auto;
	max-width: 700px;
}

.container-narrow>hr {
	margin: 20px 0;
}

/* Main marketing message and sign up button */
.jumbotron {
	margin: 10px 0;
	text-align: center;
}

.jumbotron h1 {
	font-size: 14px;
	line-height: 1;
}

.jumbotron .btn {
	font-size: 14px;
	padding: 5px 24px;
}

/* Supporting marketing content */
.marketing {
	margin: 10px 0;
}

.marketing p+h4 {
	margin-top: 10px;
}

.disabled {
	background-color: #f0f0f0;
	color: #cccccc;
}

.form-signin {
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading,.form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin input[type="text"],.form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>

</head>
<body>

	<div class="container">
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer" />
	</div>
	<!-- /container -->

</body>
</html>
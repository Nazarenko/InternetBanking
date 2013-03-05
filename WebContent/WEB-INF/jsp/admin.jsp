<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Employee page</title>
<script src="resources/js/libs/json2.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="resources/js/libs/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/underscore.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/jsrender.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/backbone.js" type="text/javascript" charset="utf-8"></script>

<!-- APP -->
<script src="resources/js/app/scripts.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/app/transactions.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/app/paginator.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/app/admin.js" type="text/javascript" charset="utf-8"></script>
<!--  -->

<link rel="stylesheet" href="resources/css/style.css" type="text/css" />
<link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="resources/css/bootstrap-responsive.min.css" type="text/css" />

<script type="text/javascript">
	$(function() {
		var adminRouter = new AdminRouter();
		Backbone.history.start();
	});
</script>

</head>
<body>

	<header>
		<div class="user-info">
			<h3>${username}</h3>
			<a href="<c:url value="/j_spring_security_logout" />">Logout</a>
		</div>
	</header>
	<section>
		<div id="contentBody"></div>
		<div id="contentFooter"></div>
	</section>
</body>
</html>
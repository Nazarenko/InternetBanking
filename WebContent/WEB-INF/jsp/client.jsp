<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Client Page</title>
<script src="js/libs/json2.js" type="text/javascript" charset="utf-8"></script>
<script src="js/libs/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="js/libs/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/libs/underscore.js" type="text/javascript" charset="utf-8"></script>
<script src="js/libs/jsrender.js" type="text/javascript" charset="utf-8"></script>
<script src="js/libs/backbone.js" type="text/javascript" charset="utf-8"></script>
<script src="js/libs/jquery.validationEngine-en.js" type="text/javascript"></script>
<script src="js/libs/jquery.validationEngine.js" type="text/javascript"></script>

<!-- APP -->
<script src="js/app/scripts.js" type="text/javascript" charset="utf-8"></script>
<script src="js/app/paginator.js" type="text/javascript" charset="utf-8"></script>
<script src="js/app/transactions.js" type="text/javascript" charset="utf-8"></script>
<script src="js/app/client.js" type="text/javascript" charset="utf-8"></script>
<!--  -->

<link rel="stylesheet" href="css/style.css" type="text/css" />
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" type="text/css" />
<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css" />
<script type="text/javascript">

	// Called before AJAX form validation call
	function beforeCall(form, options) {
		$("#message").hide();
		return true;
	}
	// Called once the server replies to the ajax form validation request
	function ajaxValidationCallback(status, form, json, options) {
		if (status === true) {
			$("#message").show();
			$("#add_transaction").find('input:text').val('');
		}
	}
	function initValidation(){
		$("#add_transaction").validationEngine({
			ajaxFormValidation : true,
			onAjaxFormComplete: ajaxValidationCallback,
			onBeforeAjaxFormValidation: beforeCall
		});

	}
	$(function() {
		var clientRouter = new ClientRouter();
		Backbone.history.start();
	});
</script>


</head>
<body>

	<header>
	 	<div class="menu" id="transactionMenu">
	 	</div>
		<div class="user-info">
			<h3>${name}</h3>
			<a href="<c:url value="/j_spring_security_logout" />">Logout</a>
		</div>
	</header>
	<section>
		<div id="contentBody"></div>
		<div id="contentFooter"></div>
	</section>

</body>
</html>
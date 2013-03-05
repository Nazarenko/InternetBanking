<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta lang="en" />
<meta charset="UTF-8" />
<title>Login Page</title>
<link rel="stylesheet" href="resources/css/style.css" type="text/css" />
<link rel="stylesheet" href="resources/css/validationEngine.jquery.css" type="text/css" />
<link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css" />
<script src="resources/js/libs/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="resources/js/libs/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/jquery.validationEngine-en.js" type="text/javascript"></script>
<script src="resources/js/libs/jquery.validationEngine.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#login-form").validationEngine();
	});
</script>
</head>
<body onload='document.f.j_username.focus();'>

	<section>
		<div id="login-form-div">
			<form id='login-form' name='f' class="form-horizontal"
				action="<c:url value='/j_spring_security_check' />" method='post'>
				<fieldset>
					<legend>Internet Banking</legend>
					<c:if test="${not empty error}">
					<div class="control-group">
						<div class="error block">
							Your login attempt was not successful, try again.<br /> Caused :
							${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
						</div>
					</div>
					</c:if>
					
					<div class="control-group">
						<label class="control-label" for="login">Login</label>
						<div class="controls"> 
							<input id='login' type='text'
								name='j_username' value=''
								class='validate[required,custom[onlyLetterNumber]]' />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">Password</label>
						<div class="controls"> 
						 	<input id='password' type='password'
								name='j_password' class='validate[required]' />
						</div>
					</div>
					<div class="control-group">
						<div class="controls"> 
							<button class="btn" type="submit">Sign in</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</section>
</body>
</html>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Client Page</title>
<script src="resources/js/libs/json2.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="resources/js/libs/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/underscore.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/jsrender.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/backbone.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/libs/jquery.validationEngine-en.js" type="text/javascript"></script>
<script src="resources/js/libs/jquery.validationEngine.js" type="text/javascript"></script>

<!-- APP -->
<script src="resources/js/app/scripts.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/app/paginator.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/app/transactions.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/app/client.js" type="text/javascript" charset="utf-8"></script>
<!--  -->

<link rel="stylesheet" href="resources/css/style.css" type="text/css" />
<link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="resources/css/bootstrap-responsive.min.css" type="text/css" />
<link rel="stylesheet" href="resources/css/validationEngine.jquery.css" type="text/css" />
<script type="text/javascript">


	$(function() {
		var clientRouter = new ClientRouter();
        clientRouter.number = ${number};
		Backbone.history.start();
	});

    function checkDestinationAccount(field, rules, i, options) {
        if (!options.isError) {
            if (this.number != field.val()) {
                this.number = field.val();
                var self = this;
                jQuery.ajax({
                    url: "accountActive/" + self.number,
                    success: function(response) {
                        self.error = null;
                    },
                    error: function(response) {
                        self.error = "* " + JSON.parse(response.responseText).error;
                    },
                    async:false
                });
            }
            if (this.error !== null) {
                return this.error;
            }
        }
    }

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
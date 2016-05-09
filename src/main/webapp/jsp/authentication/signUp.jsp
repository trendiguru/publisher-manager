<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Sign Up</title>
		<meta charset="utf-8">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/fonts.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/meyer_reset.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/main.css">
		
		<script src="js/lib/jquery-2.2.2.min.js"></script>
		<script src="js/main.js"></script>
		<script>
			$(document).ready(function() {
				manager.auth.init();
				$("#publisherName").focus();
			});
		</script>
	</head>
	
	<body>
		<header class="header-inner">
			<div id="siteTitle">TrendiGuru - Publisher Sign Up</div>
			<form id="signUpForm">
			
				<div class="container">
					<div class="colspan2" id="errorBox" style="display:none;">
						Errors!
					</div>
				
				   	<div class="row">
				       	<div class="column title">Publisher Name<input type="text" id="publisherName" name="publisher.name" value=""/></div>
				       	<div class="column title">Password<input type="password" name="publisher.password" value=""/></div>
				   	</div>
				   	<div class="row">
				       	<div class="column title">Domain http://<input type="text" name="publisher.domain" value=""/></div>
				       	<div class="column title">Repeat Password<input type="password" name="publisher.repeatPassword" value=""/></div>
				   	</div>
					<div class="row">
						<div class="column title">Email<input type="text" name="publisher.email" value=""/></div>
				   	</div>
				</div>
				
				<button type="button" id="signUp">Sign Up</button>
	   		</form>
		</header>
		
		<div id="main">
		</div>
	
	</body>
</html>
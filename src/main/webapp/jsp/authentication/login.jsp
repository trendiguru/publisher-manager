<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Login</title>
		<meta charset="utf-8">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/fonts.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/meyer_reset.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/main.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/tg_copy.css">
		
		
		<script src="js/lib/jquery-2.2.2.min.js"></script>
		<script src="js/main.js"></script>
		<script>
			$(document).ready(function() {
				manager.auth.init();
				$("#email").focus();
			});
		</script>
	</head>
	
	<body>
		<header class="header-inner">
			<div id="siteTitle">TrendiGuru - Publisher Login</div>
			<form id="loginForm">
			
				<div class="container">
					<div id="errorBox" style="display:none;">
						Errors!
					</div>
				
				
					<div class="left"><input id="email" name="user.email" class="newsletter-form-field-element field-element" name="email" x-autocompletetype="email" type="text" spellcheck="false" placeholder="Email Address"></div>
					<div class="right"><input id="password" name="user.password" class="newsletter-form-field-element field-element" name="email" type="password" spellcheck="false" placeholder="Password"></div>
					
					<a class="signUpLink" href="signUpForm">Publisher Sign Up</a>
				   	<button type="button" id="login" class="newsletter-form-button">Login</button>
				   	
				   	
				</div>
			
		   		
		   		
	   		</form>
		</header>
		
		<div id="main">
		</div>
	
	</body>
</html>
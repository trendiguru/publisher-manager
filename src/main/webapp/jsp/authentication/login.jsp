<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Login!</title>
		<meta charset="utf-8">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/fonts.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/meyer_reset.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/main.css">
		
		<script src="js/lib/jquery-2.2.2.min.js"></script>
		<script src="js/main.js"></script>
		<script>
			$(document).ready(function() {
				manager.auth.init();
			});
		</script>
	</head>
	
	<body>
		<header class="header-inner">
			<div id="siteTitle">TrendiGuru - Publisher Login</div>
			<form id="loginForm">
		   		<span class="title">Email</span><input type="text" id="email" name="user.email" value="user@digitalspy.com">
		   		<span class="title">Password</span><input type="password" id="password" name="user.password" value="mypassword"/>
		   		<button type="button" id="login">Login</button>
	   		</form>
		</header>
		
		<div id="main">
		</div>
	
	</body>
</html>
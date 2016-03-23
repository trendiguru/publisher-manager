<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Login!</title>
		<meta charset="utf-8">
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="js/main.js"></script>
	</head>
	
	<body>
		<h1>Please login</h1>
		<div>
			<form id="loginForm">
		   		Email: <input type="text"/ id="email" name="user.email">
		   		Password: <input type="password" id="password" name="user.password"/>
		   		<button type="button" id="login">Login</button>
	   		</form>
	   </div>
	</body>
</html>
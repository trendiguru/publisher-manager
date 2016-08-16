<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<%@include file="/jsp/common/include.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Publisher Sign Up</title>
		<meta charset="utf-8">
		<link rel="shortcut icon" type="image/x-icon" href="/publisher-manager/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/fonts.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/meyer_reset.css">
		
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/tg_copy.css">
		<link rel="stylesheet" type="text/css" href="/publisher-manager/css/main.css">
		
		<script src='https://www.google.com/recaptcha/api.js'></script>
		<script src="/publisher-manager/js/lib/jquery-2.2.2.min.js"></script>
		<script src="/publisher-manager/js/main.js"></script>
		<script>
			var passwordResetToken = "${passwordResetToken}";
			
			$(document).ready(function() {
				manager.auth.init();
				$("#password").focus();
			});
		</script>
	</head>
	
	<body>
		<header class="header-inner">
			<div id="siteTitle">TrendiGuru - Publisher Password Reset</div>
			
			<div id="congratulations" style="display:none;">
				<div class="title">Congratulations.  Your password has been reset</div>
				<div><a href="/publisher-manager">Click here to login to your dashboard</a></div>
			</div>
						
			<form id="signUpForm">
			
				<input type="hidden" name="publisher.passwordResetToken" id="passwordResetToken">
			
				<div class="container">
					<div id="errorBox" style="display:none;">
						Errors!
					</div>
				
					<div class="row">
						<div class="left"><input disabled name="publisher.name" id="publisherName" class="newsletter-form-field-element field-element" name="email" type="text" spellcheck="false" value="${publisher.name}"></div>
						
						<div class="center"><input disabled name="publisher.contactName" id="contactName" class="newsletter-form-field-element field-element" name="email" type="text" spellcheck="false" value="${publisher.contactName}"></div>
						<div class="right"><input disabled name="publisher.email" id="email" class="newsletter-form-field-element field-element" name="email" x-autocompletetype="email" type="text" spellcheck="false" value="${publisher.email}"></div>
						
					</div>
					
					<div class="row">
						<div class="left"><input name="publisher.password" id="password" class="newsletter-form-field-element field-element" name="email" type="password" spellcheck="false" placeholder="Password"></div>
					
						<div class="center"><input name="publisher.repeatPassword" id="repeatPassword" class="newsletter-form-field-element field-element" name="email" type="password" spellcheck="false" placeholder="Repeat Password"></div>	
						<div class="g-recaptcha right" id="rcaptcha" data-sitekey="6Lff9B8TAAAAACzOP2tVxQQ7sAZ_Cqfr2PvTXhN9"></div>
					
					</div>
					
					<div class="row hint">
						Your new password will be emailed to you after a successful reset
					</div>
					<!-- 
					<div class="row hint">
						<input type="checkbox" id="agreeToTAndC" checked> I agree to your <a class="tosPrivacyPolicyLink" href="http://www.trendiguru.com/s/ToS-Privacy-Policy.docx">Terms of Service and Privacy Policy</a>
					</div>
					-->
								
					<div class="row">
					 
						<button type="button" id="resetPassword" class="newsletter-form-button sqs-system-button sqs-editable-button-layout sqs-editable-button-style sqs-editable-button-shape">Reset Password</button>
		   		
		   			</div>
				
				   	
				</div>
			
		   		
		   		
	   		</form>
		</header>
		
		<div id="main">
		</div>
		
	</body>
</html>
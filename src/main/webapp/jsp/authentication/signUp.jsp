<%@ page language="java" pageEncoding="UTF-8" session="false"%>
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
		<script src="js/lib/jquery-2.2.2.min.js"></script>
		<script src="js/main.js"></script>
		<script>
			$(document).ready(function() {
				manager.auth.init();
				$("#domain").focus();
			});
		</script>
	</head>
	
	<body>
		<header class="header-inner">
			<div id="siteTitle">TrendiGuru - Publisher Sign Up</div>
			
			<div id="congratulations" style="display:none;">
				<div class="title">Congratulations.  Please paste the code below into the &lt;head&gt; section of your site</div>
				<div><textarea readonly id="integrate"></textarea></div>
				<div><a href="/publisher-manager">Click here to login to your dashboard</a></div>
			</div>
						
			<form id="signUpForm">
			
				<div class="container">
					<div id="errorBox" style="display:none;">
						Errors!
					</div>
				
					<div class="row">
						<div class="left"><input name="publisher.name" id="publisherName" class="newsletter-form-field-element field-element" name="email" type="text" spellcheck="false" placeholder="Company Name"></div>
						
						<div class="center"><input name="publisher.contactName" id="contactName" class="newsletter-form-field-element field-element" name="email" type="text" spellcheck="false" placeholder="Contact Name"></div>
						<div class="right"><input name="publisher.email" id="email" class="newsletter-form-field-element field-element" name="email" x-autocompletetype="email" type="text" spellcheck="false" placeholder="Contact Email"></div>
						
					</div>
					
					<div class="row">
						<div class="left"><input name="publisher.password" id="password" class="newsletter-form-field-element field-element" name="email" type="password" spellcheck="false" placeholder="Password"></div>
					
						<div class="center"><input name="publisher.repeatPassword" id="repeatPassword" class="newsletter-form-field-element field-element" name="email" type="password" spellcheck="false" placeholder="Repeat Password"></div>	
						<div class="g-recaptcha right" id="rcaptcha" data-sitekey="6Lff9B8TAAAAACzOP2tVxQQ7sAZ_Cqfr2PvTXhN9"></div>
					
					</div>
					
					<div class="row hint">
						Your login info will be emailed to you after a successful sign-up
					</div>
					
					<div class="row hint">
						<input type="checkbox" id="agreeToTAndC" checked> I agree to your <a class="tosPrivacyPolicyLink" href="http://www.trendiguru.com/s/ToS-Privacy-Policy.docx">Terms of Service and Privacy Policy</a>
					</div>
									
					<div class="row">
					 
						<button type="button" id="signUp" class="newsletter-form-button sqs-system-button sqs-editable-button-layout sqs-editable-button-style sqs-editable-button-shape">Sign Up</button>
		   		
		   			</div>
				
				   	
				</div>
			
		   		
		   		
	   		</form>
		</header>
		
		<div id="main">
		</div>
		
	</body>
</html>
var manager = manager || {
	ver: "0.1"   
};

manager.auth = {};
manager.infra = {};
manager.error = {};
manager.publisher = {};
manager.admin = {};

manager.auth.init = function() {
	console.log("ready!");
	
	$(document).ajaxStart(function() {
		manager.infra.showLoader();
	});
	
	$(document).ajaxStop(function() {
		manager.infra.hideLoader();
	});
	
	/* 'enter' submits the form */
	$('input').on("keypress", function(e) {
	     var code = (e.keyCode ? e.keyCode : e.which);
	     if (code == 13) {
	        e.preventDefault();
	        e.stopPropagation();
	        $(this).closest('form').find("button").trigger("click");
	     }
	});
	
	$("#login").click(function(e) {
		$("#errorBox").text("").hide();
		
		$.post(
			"auth/login", 
			$("#loginForm").serializeArray(), 
			function(response, status, xhr) { 
				if (manager.infra.requestIsValid(xhr)) { 
					console.log("now tell me what to do...");
					//manager.publisher.init();
					var loggedInPublisher = xhr.responseJSON;
					window.location.href = "private/publisher/dashboard?token=" + loggedInPublisher.token;
				}
			
			}, "json")
			.error(function(qXHR, textStatus, errorThrown) {
				console.log("error: " + errorThrown);
				alert("error: " + errorThrown);
			}
		);
		
	});
	
	$("#signUp").click(function(e) {
		
		var formValid = false;
		
		//console.log("validating form...");
		//manager.infra.validate($('#signUpForm').toJSO());
		var formValid = manager.infra.validate();
		
		//console.log(formValid);
		
		if (formValid) {
			console.log("submitting form...");
			$("#errorBox").text("").hide();
			
			$.post(
				"auth/signUp", 
				$("#signUpForm").serializeArray(), 
				function(response, status, xhr) { 
					if (manager.infra.requestIsValid(xhr)) { 
						console.log("now tell me what to do...");
						alert("Sign Up Success - You will be redirected to our Publisher Login page...");
						window.location.href = "/publisher-manager";
						//manager.publisher.init();
						//var loggedInPublisher = xhr.responseJSON;
						//window.location.href = "private/publisher/dashboard?token=" + loggedInPublisher.token;
					}
				
				//}, "json")
				})
				.error(function(qXHR, textStatus, errorThrown) {
					console.log("error: " + errorThrown);
					alert("error: " + errorThrown);
				}
			);
		}
	});
};

manager.infra.validate = function() {
	$("#signUpForm input").removeClass("invalid");
	
	var nullField = null;
	
	//null check
	$("#signUpForm input[type=text], #signUpForm input[type=password]").each(function() {
		//console.log(this.value);
		if (this.value == null || this.value == "") {
			nullField = this;
			return false;
		}
    });
	
	if (nullField == null) {
		//passwords are equal?
		var password = $("#signUpForm input[name='publisher.password']");
		var repeatPassword = $("#signUpForm input[name='publisher.repeatPassword']");
		
		if (password.val() == repeatPassword.val()) {
			//domain is a domain?
			var domain = $("#signUpForm input[name='publisher.domain']");
			if (domain.val().indexOf(".") > -1) {
				
				//submit form
				return true;
			} else {
				manager.infra.showErrorBox("Domain must be valid!");
				return false;
			}
		} else {
			manager.infra.showErrorBox("Passwords must match!");
			password.addClass("invalid");
			password.focus();
			repeatPassword.addClass("invalid");
			return false;
		}
	} else {
		manager.infra.showErrorBox("Please fill all empty fields!");
		$(nullField).addClass("invalid");
		$(nullField).focus();
		return false;
		
	}
	//console.log(formJson);
};

manager.infra.showErrorBox = function(message) {
	$("#errorBox").text(message).show();
	
};

manager.infra.requestIsValid = function(xhr) {
	if (xhr.getResponseHeader('rd-form-errors')) {
		//console.info(xhr);

		//var addedGenericErrorBox = false;
		var inputField = null;
		
		var jsonObject = $.parseJSON( xhr.responseText );	//convert html response to JSON object
		
		var errorText = "";
		
		if (jsonObject.fieldErrors.length > 0) {
			var j=0;
			
			for (var errorArrayElement in jsonObject.fieldErrors) {
				j++;
				//console.info(errorArrayElement +" = "+jsonObject.fieldErrors[errorArrayElement]);
				var errorKeyValuePair = jsonObject.fieldErrors[errorArrayElement];
				for (var key in errorKeyValuePair) {
					//console.info(key +" = "+errorKeyValuePair[key]);
					var value = errorKeyValuePair[key]	//nb stuts error message = "[......]"
					value = value.replace("[", "");
					value = value.replace("]", "");
					errorText += value;
					
					$("#signUpForm input[name='" + key + "']").addClass("invalid").focus();
					
					
				}
				
				if (j < (jsonObject.fieldErrors.length) ) {
					errorText += ", ";
				}
			}
			
		}

		var jsObjAELength = jsonObject.actionErrors.length;
		
		if (jsObjAELength > 0) {
			for (var i=0; i<jsObjAELength; i++) {
				var value = jsonObject.actionErrors[i];
				errorText += value + "";
				if (i < (jsObjAELength - 1) ) {
					errorText +=", ";
				}
			}
		}
	
        //alert(errorText);
        manager.infra.showErrorBox(errorText);
        
		//showNativeAlert("Oops!", errorText);
		
		return false;
	} 
	return true;
};

manager.infra.commonCallback = function(xhr) {
	if (xhr.status == 403) {
		console.warn("TRIED TO ACCESS THE SERVER WITHOUT BEING LOGGED IN!  LOAD LOGIN SCREEN!!!!!");
		return false;
	} else {
		if ( displayValidationErrors(xhr) ) {
			if ( displayNotificationMessages(xhr) ) {
				//displayBreadcrumbs(xhr);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
};

manager.infra.showLoader = function() {
	if ($("#loader").length == 0) {
		$("body").append("<div id='loader'></div>");
	} else {
		$("#loader").show();
	}
	
	$("#loader").css("height", window.innerHeight + "px");
};

manager.infra.hideLoader = function() {
	$("#loader").hide();
};

/*
$.fn.toJSO = function () {
    var obj = {},
        $kids = $(this).find('[name]');
    if (!$kids.length) {
        return $(this).val();
    }
    $kids.each(function () {
        var $el = $(this),
            name = $el.attr('name');
        if ($el.siblings("[name='" + name + "']").length) {
            if (!/radio|checkbox/i.test($el.attr('type')) || $el.prop('checked')) {
                obj[name] = obj[name] || [];
                obj[name].push($el.toJSO());
            }
        } else {
            obj[name] = $el.toJSO();
        }
    });
    return obj;
};
*/
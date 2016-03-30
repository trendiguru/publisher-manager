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
	$("#login").click(function(e) {
		
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
			}
		);
		
	});
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
	
        alert(errorText);
        
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
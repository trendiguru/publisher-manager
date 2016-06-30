$(document).ready(function() {
	manager.admin.init();
});

//admin only
manager.admin.init = function() {
	
	manager.admin.loadIframe("Trendi Guru Admin");
	
	$("#selectDashboard").change(function () {
		manager.admin.loadIframe(this.value);
	    //console.log(end);
	});
};

manager.admin.loadIframe = function(publisherName) {
	//alert("Loading dashboard for: " + publisherName);
	
	var encodedPublisherName = publisherName.replace(/ /g, "-");
	var kibanaIframeSrc = manager.admin.iframeSrc;
	kibanaIframeSrc = kibanaIframeSrc.replace(/XXXX/g, encodedPublisherName);
	$('#kibanaDashboard').attr('src', kibanaIframeSrc);
};

		
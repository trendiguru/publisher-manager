$(document).ready(function() {
	manager.admin.init();
});

//admin only
manager.admin.init = function() {
	
	manager.admin.loadIframe("Trendi Guru Admin");
	
	$("#selectDashboard").change(function () {
		var pubName = $("#" + this.id + " option:selected").text();
		
		//reset any filters
		$("#dynamicFilters").empty();
		$("#filterWrapper .addFilter").removeClass("hidden");
		manager.publisher.filters.added = 0;
		$.each(manager.publisher.filters.options, function(key, filter) {
			filter.inUse = false;
			filter.domId = null;
		});
		$("#runFilter").addClass("hidden");
		$("#errorBox").text("").hide();
		
		//event handlers on our buttons are added via the .one() jquery method		
		
		manager.publisher.populateFilters();
		manager.admin.loadIframe(pubName);
	    //console.log(end);
	});
};

manager.admin.loadIframe = function(publisherName) {
	//alert("Loading dashboard for: " + publisherName);
	
	//manager.publisher.populateFilters();
	
	var encodedPublisherName = publisherName.replace(/ /g, "-");
	var kibanaIframeSrc = manager.admin.iframeSrc;
	kibanaIframeSrc = kibanaIframeSrc.replace(/XXXX/g, encodedPublisherName);
	$('#kibanaDashboard').attr('src', kibanaIframeSrc);
};

		
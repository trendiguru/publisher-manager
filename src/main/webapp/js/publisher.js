manager.publisher.visualDataArray = [];
manager.publisher.numVisualsToDownload = 0;
manager.publisher.visualElement = 0;
/*
manager.publisher.filters = {
	"country" : {
		"operators" : ["==", "!="]
	},
	"domain" : {
		"operators" : ["==", "!="]
	}
};
*/
manager.publisher.init = function() {
	
	manager.publisher.token = window.location.search.split("token=")[1];
	
	manager.publisher.initIFrameListener();
	
	$("#downloadDashboard").click(function(e) {
		manager.infra.showLoader();
		
		manager.publisher.visualDataArray = [];
		manager.publisher.visualElement = 0;
		manager.publisher.numVisualsToDownload = $("#kibanaDashboard").contents().find("visualize-spy .visualize-show-spy").length;
		manager.publisher.getVisualData();
	});
	
	$("#signOut").click(function(e) {
		$.get(
			"/publisher-manager/auth/signOut?token=" + manager.publisher.token, 
			//$("#loginForm").serializeArray(), 
			function(response, status, xhr) { 
				if (manager.infra.requestIsValid(xhr)) { 
					console.log("now tell me what to do...");
					window.location.href = "/publisher-manager";
				}
			
			})
			.error(function(qXHR, textStatus, errorThrown) {
				console.log("error: " + errorThrown);
			}
		);
	});
	
	$(window).resize(function() {
		 manager.publisher.resizeIframe($("#kibanaDashboard"));
	});
	
	$("#runFilter").click(function(e) {
		
		//manager.publisher.buildFilter2();
		
		
		var luceneFilter = manager.publisher.buildFilter2();
		if (luceneFilter == manager.publisher.filters.lastQueryRun) {
			console.log("Nothing to run - new query = previous query!");
			manager.infra.showErrorBox("Please change the filter before running!");
		} else {
			$("#errorBox").text("").hide();
			manager.publisher.filters.lastQueryRun = luceneFilter;
		
		
			if (manager.publisher.filters.added == 0) {
				$("#runFilter").addClass("hidden");
			}
			
			var iframeDOM = $('#kibanaDashboard').contents();
			iframeDOM.find("#kibana-body .dashboard-container navbar form.inline-form input.form-control").val(luceneFilter);
			iframeDOM.find("#kibana-body .dashboard-container navbar form.inline-form button[type=submit]").click();
			
		}
	});
	
	
	manager.publisher.populateFilters();
	//var a = decodeHTMLEntities(escapedKibanaHTML);
	//$('#kibanaDashboard')[0].contentDocument.write(a);
	/*
	$.post(
			"private/publisher-dashboard", 
			//$("#loginForm").serializeArray(), 
			function(response, status, xhr) { 
				if (manager.infra.requestIsValid(xhr)) { 
					console.log("now tell me what to do...");
				}
			
			}, "json")
			.error(function(qXHR, textStatus, errorThrown) {
				console.log("error: " + errorThrown);
			}
		);
		*/
};

manager.publisher.populateFilters = function() {
	console.log("getting filter data...");
	var pid = "";
	if (manager.admin) {
		var publisherName = $("#selectDashboard").val();
		var encodedPublisherName = publisherName.replace(/ /g, "-");
		pid = "&pidOveride=" + encodedPublisherName;
	}
	
	$.getJSON(
		"/publisher-manager/private/publisher/dashboardFilterData?token=" + manager.publisher.token + pid, 
		//$("#loginForm").serializeArray(), 
		function(response, status, xhr) { 
			if (manager.infra.requestIsValid(xhr)) { 
				console.log(xhr.responseJSON);
				
				/* populate countries */
				/*
				var countrySelect = $("#countryFilter");
				var countryitems;
				$.each(xhr.responseJSON.countries, function(index, value){
					countryitems += '<option value=' + value + '>' + value + '</option>';
				});
				countrySelect.empty().append(countryitems);
				*/
				
				/* populate countries */
				/*
				var domainSelect = $("#domainFilter");
				var domainitems;
				$.each(xhr.responseJSON.domains, function(index, value){
					domainitems += '<option value=' + value + '>' + value + '</option>';
				});
				domainSelect.empty().append(domainitems);
				*/
				
				// new dynamic filters to replace the filters above
				manager.publisher.filters.options.country.values = xhr.responseJSON.countries;
				manager.publisher.filters.options.domain.values = xhr.responseJSON.domains;
				
				$('#filterWrapper').off('click', '.addFilter');
				$('#filterWrapper').off('click', '.removeFilter');
				$('#filterWrapper').off('change', '.filterList');
				
				manager.publisher.filters.init();
				
				//TODO - remove the old static filters
			}
		
		})
		.error(function(qXHR, textStatus, errorThrown) {
			console.log("error: " + errorThrown);
		}
	);
};

manager.publisher.initIFrameListener = function() {
	var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
	var eventer = window[eventMethod];
	var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";

	// Listen to message from child window
	eventer(messageEvent,function(e) {
	    var key = e.message ? "message" : "data";
	    var data = e[key];	//json is provided by Kibana: data.content, data.title
	    console.log(e);
	    manager.publisher.buildAggregatedCSV(data);
	},false);
};

manager.publisher.getVisualData = function() {
	var visualSpySection = $("#kibanaDashboard").contents().find("visualize-spy .visualize-show-spy").eq(manager.publisher.visualElement);
	if (visualSpySection.find("i").hasClass("fa-chevron-up")) {
		//the visual spy section is hidden, so show it by clicking on the up-arrow/chevron
		visualSpySection.trigger("click");
	}
	
	//$("#kibanaDashboard").contents().find("visualize-spy .visualize-show-spy").eq(manager.publisher.visualElement).trigger("click");
	if ( $("#kibanaDashboard").contents().find("visualize-spy .visualize-spy-container .pagination-container").length > 0 ) {
		$("#kibanaDashboard").contents().find("visualize-spy .visualize-spy-container .pagination-container .agg-table-controls a.small[ng-click='aggTable.getVisualData()']").eq(0)[0].click();
		//setTimeout(function() {
		
		//close the chevron section
		$("#kibanaDashboard").contents().find("visualize-spy .visualize-show-spy").eq(manager.publisher.visualElement).trigger("click");
			
		//}, 100);
	} else {
		if (manager.publisher.visualElement < manager.publisher.numVisualsToDownload) {
			console.log("Bypassing visual element: " + manager.publisher.visualElement);

			//close the empty chevron section
			$("#kibanaDashboard").contents().find("visualize-spy .visualize-show-spy").eq(manager.publisher.visualElement).trigger("click");
			
			manager.publisher.visualElement++;
			manager.publisher.getVisualData(manager.publisher.visualElement);
		}
	}
};

/**
 * Build an associated array: element name = visual title, element content = visual data
 * 
 * Create a BLOB from this array
 * 
 * Create an <a> HTML node, set its href to the BLOB url.  Click the <a> to open the save-as dialog box
 */
manager.publisher.buildAggregatedCSV = function(visualData) {
	manager.publisher.visualDataArray[visualData.title] = visualData.content;
	manager.publisher.visualElement++;

	if (manager.publisher.visualElement < manager.publisher.numVisualsToDownload) {
		//console.log("Bypassing visual element: " + manager.publisher.visualElement);
		//manager.publisher.visualElement++;
		manager.publisher.getVisualData();
	} else {
		console.log("Received all visual data!");
		//console.log(manager.publisher.visualDataArray);
		
		//1. build blob and provide download box
		var content = "";

		//nb it's not possible to bold the title in a CSV, it's just plain text.  To bold, save as .xls
		for (var visualTitle in manager.publisher.visualDataArray) {
			var visualContent = manager.publisher.visualDataArray[visualTitle];
			content += visualTitle + "\r\n";
			content += visualContent;
			content += "\r\n";
		}
		var blob = new Blob([content], { type: 'text/plain;charset=utf-8' });
		
		//2. create <a> node
		var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a");
		
		//3. create blob:http:// url pointing to the blob on disk
		var object_url = URL.createObjectURL(blob);
		save_link.href = object_url;
		save_link.download = "Trendiguru_Analytics.csv";
		
		manager.infra.hideLoader();
		
		setTimeout(function() {
			var event = new MouseEvent("click");
			save_link.dispatchEvent(event);
			
			//4. remove the blob: url from the browser
			URL.revokeObjectURL(object_url);
			//filesaver.readyState = filesaver.DONE;
		});
	}
	
};

//TODO - delete? - old way to click manually on ALL download link inside a visual and save each one separately
/*
manager.publisher.downloadOne = function(visualElement, numOfElements) {
	$("#kibanaDashboard").contents().find("visualize-spy .visualize-show-spy").eq(visualElement).trigger("click");
	if ( $("#kibanaDashboard").contents().find("visualize-spy .visualize-spy-container .pagination-container").length > 0 ) {
		$("#kibanaDashboard").contents().find("visualize-spy .visualize-spy-container .pagination-container .agg-table-controls a.small[ng-click='aggTable.exportAsCsv(true)']").eq(0)[0].click();
		setTimeout(function() {
			$("#kibanaDashboard").contents().find("visualize-spy .visualize-show-spy").eq(visualElement).trigger("click");
			
			if (visualElement < numOfElements) {
				console.log("Downloading visual element: " + visualElement);
				visualElement++;
				manager.publisher.downloadOne(visualElement, numOfElements);
			}
		}, 100);
	} else {
		if (visualElement < numOfElements) {
			console.log("Bypassing visual element: " + visualElement);
			visualElement++;
			manager.publisher.downloadOne(visualElement, numOfElements);
		}
	}
};
*/

//TODO - delete??
/*
function decodeHTMLEntities(text) {
    var entities = [
        ['apos', '\''],
        ['amp', '&'],
        ['lt', '<'],
        ['gt', '>'],
        ['quot', '"']
    ];

    for (var i = 0, max = entities.length; i < max; ++i) 
        text = text.replace(new RegExp('&'+entities[i][0]+';', 'g'), entities[i][1]);

    return text;
}
*/

manager.publisher.resizeIframe = function(iframe) {
	var viewportHeight = window.innerHeight;
	var initialIFrameheight = viewportHeight - 90;
	
    //obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
	//obj.style.height = initialIFrameheight + 'px';
	iframe.css("height", initialIFrameheight + "px");
};

/**
 * build Lucene query eg "geoip.country_name: France AND publisherDomain: fashionseoul.com"
 */
/*
manager.publisher.buildFilter = function(e) {
	//console.info('hi');
	var countryFilterValue = $("#countryFilter").val();
	var domainFilterValue = $("#domainFilter").val();
	//console.info(countryFilterValue + ", " + domainFilterValue);
	
	var luceneSearch = "*";
	var luceneCountryFilter, luceneDomainFilter;
	
	if (countryFilterValue != "All") {
		luceneCountryFilter = "geoip.country_name:" + countryFilterValue;
	}
	
	if (domainFilterValue != "All") {
		luceneDomainFilter = "publisherDomain:" + domainFilterValue;
	}
	
	if (luceneCountryFilter != null) {
		luceneSearch = luceneCountryFilter;
	}
	
	if (luceneDomainFilter != null) {
		if (luceneSearch == "*") {
			luceneSearch = luceneDomainFilter;
		} else {
			luceneSearch += " AND " + luceneDomainFilter;
		}
	}
	
	console.log(luceneSearch);
	return luceneSearch;
};
*/

manager.publisher.buildFilter2 = function() {
	var luceneSearch = "";
	
	var i = 0;
	$.each(manager.publisher.filters.options, function(key, filter) {
		i++;
		if (filter.inUse) {
			var value = $("#" + filter.domId + " .filterValues").val();
			
			if (value != "All") {
				luceneSearch += filter.lucenceQuery + ":" + value;
			}
			
			
			if (i < manager.publisher.filters.added && value != "All") {
				luceneSearch += " AND ";
			}
			
		}
	});
	
	if (luceneSearch.endsWith(" AND ")) {
		luceneSearch = luceneSearch.substring(0, (luceneSearch.length-5));
	}
	
	if (luceneSearch == "") {
		luceneSearch = "*";
	}
	console.log(luceneSearch);
	return luceneSearch;
};

manager.publisher.filters.init = function() {
	$('#filterWrapper').on('click', '.addFilter', manager.publisher.filters.add);
	$('#filterWrapper').on('click', '.removeFilter', manager.publisher.filters.remove);
	$('#filterWrapper').on('change', '.filterList', function(e) {
		console.log("change values");
		var updatedOptionsHTML = "";
		var newFilterKey = $(this).val();
		
		$.each(manager.publisher.filters.options[newFilterKey].values, function(index, value){
			updatedOptionsHTML += '<option value=' + value + '>' + value + '</option>';
		});
				
		$(this).parent().find(".filterValues").empty();
		$(this).parent().find(".filterValues").append(updatedOptionsHTML);
	});
	
	//manager.publisher.filters.add();
};
manager.publisher.filters.remove = function(filter) {
	//console.log(filter);
	var $parent = $(this).parent();
	manager.publisher.filters.added--;
	$parent.remove();
	if (manager.publisher.filters.added < Object.keys(manager.publisher.filters.options).length) {
		$("#filterWrapper .addFilter").removeClass("hidden");
	}
	
	//enable the preceeding filter's dropdown
	$('#filterWrapper .filterList:not(:last-child)').prop('disabled', false);
	
	manager.publisher.filters.options[$parent.attr("data-name")].inUse = false;
	
	manager.infra.showErrorBox("Click 'Run filter' when your filter is ready!");
	
	var luceneFilter = manager.publisher.buildFilter2();
	if (manager.publisher.filters.added == 0) {
		
		$("#errorBox").text("").hide();
		
		if (manager.publisher.filters.lastQueryRun == luceneFilter) {
			//$("#runFilter").click();
			$("#runFilter").addClass("hidden");
		} else {
			$("#runFilter").click();
			$("#runFilter").addClass("hidden");
		}
	}
	
};

manager.publisher.filters.add = function() {
		
	var filterNameOptionsHTML = "";
	var filterOperatorOptionsHTML = ""; 
	var filterValueOptionsHTML = "";
	/*
	for (var i=0; i<manager.publisher.filters.names.length; i++) {
		filterNameOptionsHTML += '<option value="' + manager.publisher.filters.names[i] + '">' + manager.publisher.filters.names[i] + '</option>';
	}
	
	for (var i=0; i<manager.publisher.filters.operators.length; i++) {
		filterOperatorOptionsHTML += '<option value="' + manager.publisher.filters.operators[i] + '">' + manager.publisher.filters.operators[i] + '</option>';
	}
	 */
	var populateOptions = true;
	var filterName = "";
	
	manager.publisher.filters.added++;
	//var existingNumOfFilters = manager.publisher.filters.added;
	var filterId = "filter_" + manager.publisher.filters.added;
	
	$.each(manager.publisher.filters.options, function(key, filter) {
		//i++;
	    //alert(key + ' ' + value);
		if (!filter.inUse) {
	
			filterNameOptionsHTML += '<option value="' + key + '">' + filter.name + '</option>';
			if (populateOptions) {
				filterName = key;
				
				for (var i=0; i< filter.operators.length; i++) {
					filterOperatorOptionsHTML += '<option value="' + filter.operators[i] + '">' + filter.operators[i] + '</option>';
				}
				
				$.each(filter.values, function(index, value){
					filterValueOptionsHTML += '<option value=' + value + '>' + value + '</option>';
				});
				
				populateOptions = false;
				filter.inUse = true;
				filter.domId = filterId;
			}
			
			
		}
		
		//TODO - add operators per filter here
		manager.infra.showErrorBox("Click 'Run filter' when your filter is ready!");
	});
	
/*
	$.each(manager.publisher.filters.countries, function(index, value){
		filterValueOptionsHTML += '<option value=' + value + '>' + value + '</option>';
	});
*/
	
	
	//populate handlebar template with select options
	var source = $("#filter-template").html();
	var template = Handlebars.compile(source);
	var context = {id: filterId, name: filterName, filterNameOptions: filterNameOptionsHTML, filterOperatorOptions: filterOperatorOptionsHTML, filterValueOptions: filterValueOptionsHTML};
	var html = template(context);
	
	if (manager.publisher.filters.added > 0) {
		$("#filterWrapper .filterList").prop('disabled', true);
	}
	
	//add new filter to DOM
	$("#dynamicFilters").append(html);
	
	
	//disable any previous filters' 1st drop-down to prevent the possibility of the user selecting the same filter > 1 !
	//
	//TODO
	//
	//
	
		
	//add to array
	//manager.publisher.filters.added++;
	//manager.publisher.filters.added[filterId] = "aaaaa";
	
	//only allow adding as many filters as exist
	if ( (manager.publisher.filters.added) == Object.keys(manager.publisher.filters.options).length) {
		$("#filterWrapper .addFilter").addClass("hidden");
	}
	
	$("#runFilter").removeClass("hidden");
	
	return filterId;
};

$(document).ready(function() {
	manager.publisher.init();
});
manager.publisher.visualDataArray = [];
manager.publisher.numVisualsToDownload = 0;
manager.publisher.visualElement = 0;

manager.publisher.init = function() {
	
	manager.publisher.initIFrameListener();
	
	$("#downloadDashboard").click(function(e) {
		manager.publisher.visualDataArray = [];
		manager.publisher.visualElement = 0;
		manager.publisher.numVisualsToDownload = $("#kibanaDashboard").contents().find("visualize-spy .visualize-show-spy").length;
		manager.publisher.getVisualData();
	});
	
	$("#signOut").click(function(e) {
		
	});
	
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
		$("#kibanaDashboard").contents().find("visualize-spy .visualize-show-spy").eq(manager.publisher.visualElement).trigger("click");
			
		//}, 100);
	} else {
		if (manager.publisher.visualElement < manager.publisher.numVisualsToDownload) {
			console.log("Bypassing visual element: " + manager.publisher.visualElement);
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

$(document).ready(function() {
	manager.publisher.init();
});
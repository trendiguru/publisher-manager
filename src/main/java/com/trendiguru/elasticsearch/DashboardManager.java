package com.trendiguru.elasticsearch;

import java.util.List;

import com.trendiguru.entities.Publisher;

public class DashboardManager extends ElasticSearchManager {

	public DashboardManager() {
	}
	
	public String getESEntityUrl(String visualName) {
		return "http://localhost:9000/elasticsearch/.kibana/dashboard/" + visualName + "?op_type=create";
	}
	
	public void addDashBoard(Publisher publisher, List<String> visualsList) {
		String visualEncodedJSON = jsonForDashboard(publisher, visualsList);
    	send(publisher, visualEncodedJSON, "Dashboard-for-" + publisher.getName());
	}
	
	//TODO - use List
	public String jsonForDashboard(Publisher publisher, List<String> visualsList) {
    	String json = "{" +
    			"\"title\": \"" + publisher.getName() + " Dashboard\"," +
    			"\"hits\": 0," +
    			"\"description\": \"\"," +
    			
				"\"panelsJSON\": \"[{\\\"id\\\":\\\"Data-Table-for-Publisher-" + publisher.getName() + "\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":1,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":1},{\\\"id\\\":\\\"Histogram-for-Publisher-" + publisher.getName() + "\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":2,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":4,\\\"row\\\":1}]\"," +
				//"\"panelsJSON\": \"[{\\\"id\\\":\\\"Data-Table-for-Publisher-DigitalSpy\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":1,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":1},{\\\"id\\\":\\\"Histogram-for-Publisher-DigitalSpy\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":2,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":4,\\\"row\\\":1}]\"," +
					
    			"\"optionsJSON\": \"{\\\"darkTheme\\\":false}\"," +
    			"\"uiStateJSON\": \"{}\"," +
    			"\"version\": 1," +
    			"\"timeRestore\": true," +
    			"\"timeTo\": \"now/M\"," +
    			"\"timeFrom\": \"now/M\"," +
    			"\"kibanaSavedObjectMeta\": {" +
    		
    				"\"searchSourceJSON\": \"{\\\"filter\\\":[{\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}}}]}\"" +
    			"}" +
    		"}";
    	
    	return json;
    }

}

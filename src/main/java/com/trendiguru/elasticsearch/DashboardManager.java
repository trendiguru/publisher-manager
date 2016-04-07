package com.trendiguru.elasticsearch;

import java.util.Set;

import com.trendiguru.entities.Publisher;
import com.trendiguru.entities.visuals.Visual;
import com.trendiguru.entities.visuals.VisualEnum;

public class DashboardManager extends KibanaManager {

	Publisher publisher;
	
	public DashboardManager(Publisher publisher) {
		this.publisher  = publisher;
	}
	
	public String getESEntityUrl(String visualId) {
		return "http://localhost:9000/elasticsearch/.kibana/dashboard/" + visualId + "?op_type=create";
	}
	
	public void addDashBoard(Set<Visual> visualsToAddSet) {
		String visualEncodedJSON = jsonForDashboard(visualsToAddSet);
    	add(publisher, visualEncodedJSON, publisher.getEncodedName() + "-dashboard");
	}
	
	/**
	 * 
	 * @param visualsToAddSet - it's hard to have a dynamic set of visuals since their layout will vary of the no. of visuals varies! So for
	 * now, the visuals are fixed!
	 * 
	 * @return
	 */
	public String jsonForDashboard(Set<Visual> visualsToAddSet) {
		/*
    	String json = "{" +
    			"\"title\": \"" + publisher.getName() + " Dashboard\"," +
    			"\"hits\": 0," +
    			"\"description\": \"\"," +
				"\"panelsJSON\": \"[{\\\"id\\\":\\\"Data-Table-for-Publisher-" + publisher.getName() + "\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":1,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":1},{\\\"id\\\":\\\"Histogram-for-Publisher-" + publisher.getName() + "\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":2,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":4,\\\"row\\\":1}]\"," +	
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
    	*/
    	String json = "{" +
			"\"title\":\"" + publisher.getName() + " Dashboard\"," +
			"\"hits\":0," + 
			"\"description\":\"\"," +
			"\"panelsJSON\":\"[" +
			
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-events-breakdown\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":1,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":1,\\\"row\\\":5}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-world-map\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":2,\\\"size_x\\\":12,\\\"size_y\\\":4,\\\"col\\\":1,\\\"row\\\":1}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-devices\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":3,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":7,\\\"row\\\":5}" +
			
			"]\"," +
			"\"optionsJSON\":\"{\\\"darkTheme\\\":false}\"," +
			"\"uiStateJSON\":\"{\\\"P-2\\\":{\\\"spy\\\":{\\\"mode\\\":{\\\"name\\\":null,\\\"fill\\\":false}}}}\"," +
			"\"version\":1," +
			"\"timeRestore\":true," +
			"\"timeTo\":\"now/y\"," +
			"\"timeFrom\":\"now/y\"," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"filter\\\":[{\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}}}]}\"" +
			"}" +
    	"}";
    	
    	return json;
    }

}

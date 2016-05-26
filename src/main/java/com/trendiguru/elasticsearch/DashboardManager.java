package com.trendiguru.elasticsearch;

import java.util.Set;

import com.trendiguru.entities.User;
import com.trendiguru.entities.visuals.Visual;
import com.trendiguru.entities.visuals.VisualEnum;

public class DashboardManager extends KibanaManager {

	User publisher;
	
	public DashboardManager(User publisher) {
		this.publisher  = publisher;
	}
	
	public String getESEntityUrl(String visualId) {
		return "/elasticsearch/.kibana/dashboard/" + visualId + "?op_type=create";
	}
	
	public void addDashBoard(Set<Visual> visualsToAddSet) {
		String visualEncodedJSON = jsonForDashboard(visualsToAddSet);
    	add(publisher, visualEncodedJSON, publisher.getEncodedName() + "-Dashboard");
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
		String s = "" +
				"{" +
				"\"title\":\"Jeremy Test Dashboard\"," +
				"\"hits\":0," +
				"\"description\":\"\"," +
				"\"panelsJSON\":\"[" +
					"{\\\"col\\\":1,\\\"id\\\":\\\"Jeremy-Test-Events-Breakdown\\\",\\\"panelIndex\\\":1,\\\"row\\\":5,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\"Jeremy-Test-World-Map\\\",\\\"panelIndex\\\":2,\\\"row\\\":1,\\\"size_x\\\":12,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\"Jeremy-Test-Devices\\\",\\\"panelIndex\\\":3,\\\"row\\\":5,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\"Jeremy-Test-Click-Thru-Rate-On-Our-Icon\\\",\\\"panelIndex\\\":4,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\"Jeremy-Test-Click-Thru-Rate-On-Items\\\",\\\"panelIndex\\\":5,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"id\\\":\\\"Jeremy-Test-Top-20-Trending-Images\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":10}," +
					"{\\\"id\\\":\\\"Jeremy-Test-Unique-Users\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":7,\\\"size_x\\\":4,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":12}," +
					"{\\\"id\\\":\\\"Jeremy-Test-Top-20-Trending-Categories\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"col\\\":7,\\\"row\\\":10}," +
					"{\\\"id\\\":\\\"Test-Average-Time-on-Site\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":9,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":5,\\\"row\\\":12}" +
				"]\",\"optionsJSON\":\"{\\\"darkTheme\\\":false}\",\"uiStateJSON\":\"{\\\"P-2\\\":{\\\"spy\\\":{\\\"mode\\\":{\\\"name\\\":null,\\\"fill\\\":false}}}}\",\"version\":1,\"timeRestore\":false,\"kibanaSavedObjectMeta\":{\"searchSourceJSON\":\"{\\\"filter\\\":[{\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}}}]}\"}}";
				*/
				
    	String json = "{" +
			"\"title\":\"" + publisher.getName() + " Dashboard\"," +
			"\"hits\":0," + 
			"\"description\":\"\"," +
			"\"panelsJSON\":\"[" +
			
				"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Events-Breakdown\\\",\\\"panelIndex\\\":1,\\\"row\\\":5,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-World-Map\\\",\\\"panelIndex\\\":2,\\\"row\\\":1,\\\"size_x\\\":12,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Devices\\\",\\\"panelIndex\\\":3,\\\"row\\\":5,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Our-Icon\\\",\\\"panelIndex\\\":4,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Items\\\",\\\"panelIndex\\\":5,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Top-20-Trending-Images\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":10}," +
				
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Unique-Users\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":7,\\\"size_x\\\":4,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":12}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Top-20-Trending-Categories\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"col\\\":7,\\\"row\\\":10}," +

				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Average-Time-on-Site\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":9,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":5,\\\"row\\\":12}" +

				
				/*
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-events-breakdown\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":1,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":1,\\\"row\\\":5}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-world-map\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":2,\\\"size_x\\\":12,\\\"size_y\\\":4,\\\"col\\\":1,\\\"row\\\":1}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-devices\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":3,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":7,\\\"row\\\":5}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-click-thru-rate-our-icon\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":4,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":8}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-click-thru-rate-item\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":5,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"col\\\":7,\\\"row\\\":8}" +
				*/
			
			
			"]\"," +
			"\"optionsJSON\":\"{\\\"darkTheme\\\":false}\"," +
			"\"uiStateJSON\":\"{\\\"P-2\\\":{\\\"spy\\\":{\\\"mode\\\":{\\\"name\\\":null,\\\"fill\\\":false}}}}\"," +
			"\"version\":1," +
			"\"timeRestore\":false," +
			/*
			"\"timeRestore\":true," +
			"\"timeTo\":\"now/M\"," +
			"\"timeFrom\":\"now/M\"," +
			*/
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"filter\\\":[{\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}}}]}\"" +
			"}" +
    	"}";
    	
    	return json;
    }

}

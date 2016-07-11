package com.trendiguru.elasticsearch;

import java.util.Set;

import com.trendiguru.entities.User;
import com.trendiguru.entities.visuals.Visual;

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
		
		String json = "{" +
				"\"title\":\"" + publisher.getName() + " Dashboard\"," +
				"\"hits\":0," +
				"\"description\":\"\"," +
				"\"panelsJSON\":\"[" +
					"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-World-Map\\\",\\\"panelIndex\\\":2,\\\"row\\\":10,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Devices\\\",\\\"panelIndex\\\":3,\\\"row\\\":3,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Our-Icon\\\",\\\"panelIndex\\\":4,\\\"row\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Items\\\",\\\"panelIndex\\\":5,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Top-20-Trending-Clicked-Images\\\",\\\"panelIndex\\\":6,\\\"row\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":4,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Unique-Users\\\",\\\"panelIndex\\\":7,\\\"row\\\":1,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Top-20-Trending-Categories\\\",\\\"panelIndex\\\":8,\\\"row\\\":10,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Average-Time-on-Site\\\",\\\"panelIndex\\\":9,\\\"row\\\":1,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Revenue\\\",\\\"panelIndex\\\":10,\\\"row\\\":1,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Events-Breakdown\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":11,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":1,\\\"row\\\":3}" +
				"]\",\"optionsJSON\":\"{\\\"darkTheme\\\":false}\"," +
				"\"uiStateJSON\":\"{\\\"P-2\\\":{\\\"spy\\\":{\\\"mode\\\":{\\\"fill\\\":false,\\\"name\\\":null}}}}\"," +
				"\"version\":1," +
				"\"timeRestore\":false," +
					"\"kibanaSavedObjectMeta\":{\"searchSourceJSON\":\"{\\\"filter\\\":[{\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}}}]}\"" +
				"}" +
			"}";
		/*
		String json = "{" +
				"\"title\":\"" + publisher.getName() + " Dashboard\"," +
				"\"hits\":0," +
				"\"description\":\"\"," +
				"\"panelsJSON\":\"[" +
					"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Events-Breakdown\\\",\\\"panelIndex\\\":1,\\\"row\\\":3,\\\"size_x\\\":6,\\\"size_y\\\":5,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-World-Map\\\",\\\"panelIndex\\\":2,\\\"row\\\":12,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Devices\\\",\\\"panelIndex\\\":3,\\\"row\\\":3,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Our-Icon\\\",\\\"panelIndex\\\":4,\\\"row\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Items\\\",\\\"panelIndex\\\":5,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Top-20-Trending-Images\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"col\\\":1,\\\"row\\\":8}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Unique-Users\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":7,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":4,\\\"row\\\":1}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Top-20-Trending-Categories\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":7,\\\"row\\\":10}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Average-Time-on-Site\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":9,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":7,\\\"row\\\":1}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Revenue\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":10,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":1}" +
				"]\",\"optionsJSON\":\"{\\\"darkTheme\\\":false}\"," +
				"\"uiStateJSON\":\"{\\\"P-2\\\":{\\\"spy\\\":{\\\"mode\\\":{\\\"name\\\":null,\\\"fill\\\":false}}}}\"," +
				"\"version\":1," +
				"\"timeRestore\":false," +
					"\"kibanaSavedObjectMeta\":{\"searchSourceJSON\":\"{\\\"filter\\\":[{\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}}}]}\"" +
				"}" +
			"}";
		*/
    	return json;
    }

}

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
		
		return "{" +
				"\"title\":\"" + publisher.getName() + " Dashboard\"," +
				"\"hits\":0," +
				"\"description\":\"\"," +
				"\"panelsJSON\":\"[" +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Our-Icon\\\",\\\"panelIndex\\\":4,\\\"row\\\":9,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Items\\\",\\\"panelIndex\\\":5,\\\"row\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Top-20-Trending-Clicked-Images\\\",\\\"panelIndex\\\":6,\\\"row\\\":12,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Unique-Users\\\",\\\"panelIndex\\\":7,\\\"row\\\":12,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":10,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Average-Time-on-Site\\\",\\\"panelIndex\\\":9,\\\"row\\\":12,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Revenue\\\",\\\"panelIndex\\\":10,\\\"row\\\":14,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Events-Breakdown\\\",\\\"panelIndex\\\":12,\\\"row\\\":1,\\\"size_x\\\":12,\\\"size_y\\\":5,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-World-Map\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":13,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"col\\\":1,\\\"row\\\":16}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Devices\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":14,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":1,\\\"row\\\":9}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Event-Totals\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":15,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":1,\\\"row\\\":6}" +
				"]\",\"optionsJSON\":\"{\\\"darkTheme\\\":false}\"," +
				"\"uiStateJSON\":\"{}\"," +
				"\"version\":1," +
				"\"timeRestore\":false," +
					"\"kibanaSavedObjectMeta\":{\"searchSourceJSON\":\"{\\\"filter\\\":[{\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}}}]}\"" +
				"}" +
			"}";

    }

}

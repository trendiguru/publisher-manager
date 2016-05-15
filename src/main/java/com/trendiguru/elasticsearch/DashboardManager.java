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
		return "/elasticsearch/.kibana/dashboard/" + visualId + "?op_type=create";
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
		String s = "{" +
				"\"title\":\"Fashion Seoul Dashboard 2\"," +
				"\"hits\":0," +
				"\"description\":\"\"," +
				"\"panelsJSON\":\"[" +
					"{\\\"col\\\":1,\\\"id\\\":\\\"fashion-seoul-events-breakdown\\\",\\\"panelIndex\\\":1,\\\"row\\\":5,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\"fashion-seoul-world-map\\\",\\\"panelIndex\\\":2,\\\"row\\\":1,\\\"size_x\\\":12,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\"fashion-seoul-devices\\\",\\\"panelIndex\\\":3,\\\"row\\\":5,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\"fashion-seoul-click-thru-rate-our-icon\\\",\\\"panelIndex\\\":4,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\"fashion-seoul-click-thru-rate-item\\\",\\\"panelIndex\\\":5,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"id\\\":\\\"fashion-seoul-trending-images\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":10}," +
					"{\\\"id\\\":\\\"fashion-seoul-unique-users\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":7,\\\"size_x\\\":4,\\\"size_y\\\":2,\\\"col\\\":7,\\\"row\\\":10}" +
				"]\",\"optionsJSON\":\"{\\\"darkTheme\\\":false}\",\"uiStateJSON\":\"{\\\"P-2\\\":{\\\"spy\\\":{\\\"mode\\\":{\\\"name\\\":null,\\\"fill\\\":false}}}}\",\"version\":1,\"timeRestore\":true,\"timeTo\":\"now\/M\",\"timeFrom\":\"now\/M\",\"kibanaSavedObjectMeta\":{\"searchSourceJSON\":\"{\\\"filter\\\":[{\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}}}]}\"}}";
		*/
		
    	String json = "{" +
			"\"title\":\"" + publisher.getName() + " Dashboard\"," +
			"\"hits\":0," + 
			"\"description\":\"\"," +
			"\"panelsJSON\":\"[" +
			
				"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-events-breakdown\\\",\\\"panelIndex\\\":1,\\\"row\\\":5,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-world-map\\\",\\\"panelIndex\\\":2,\\\"row\\\":1,\\\"size_x\\\":12,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-devices\\\",\\\"panelIndex\\\":3,\\\"row\\\":5,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-click-thru-rate-our-icon\\\",\\\"panelIndex\\\":4,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-click-thru-rate-item\\\",\\\"panelIndex\\\":5,\\\"row\\\":8,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-trending-images\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":2,\\\"col\\\":1,\\\"row\\\":10}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-unique-users\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":7,\\\"size_x\\\":4,\\\"size_y\\\":2,\\\"col\\\":7,\\\"row\\\":10}" +
				
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

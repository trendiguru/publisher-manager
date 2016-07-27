package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

/**
 * Histogram
 * Filtered by: publisher domain
 * Showing TrendiGuru events
 * 
 * @author Jeremy
 *
 */
public class EventsMultiLineHistogramVisual extends Visual {

	public EventsMultiLineHistogramVisual(User publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-events-breakdown";
		this.title = publisher.getName() + " Events Breakdown";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		
		return "{" +
				"\"title\": \"" + this.title + "\"," +
				"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"line\\\",\\\"params\\\":{\\\"shareYAxis\\\":true,\\\"addTooltip\\\":true,\\\"addLegend\\\":true,\\\"showCircles\\\":true,\\\"smoothLines\\\":false,\\\"interpolate\\\":\\\"linear\\\",\\\"scale\\\":\\\"log\\\",\\\"drawLinesBetweenPoints\\\":true,\\\"radiusRatio\\\":9,\\\"times\\\":[],\\\"addTimeMarker\\\":false,\\\"defaultYExtents\\\":false,\\\"setYExtents\\\":false,\\\"yAxis\\\":{}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"date_histogram\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"@timestamp\\\",\\\"interval\\\":\\\"auto\\\",\\\"customInterval\\\":\\\"2h\\\",\\\"min_doc_count\\\":1,\\\"extended_bounds\\\":{},\\\"customLabel\\\":\\\"Date\\\"}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"group\\\",\\\"params\\\":{\\\"field\\\":\\\"event.raw\\\",\\\"exclude\\\":{\\\"pattern\\\":\\\"\\\"},\\\"include\\\":{\\\"pattern\\\":\\\"\\\\\\\"Page%20Hit\\\\\\\"|\\\\\\\"Trendi%20Button%20Clicked\\\\\\\"|\\\\\\\"Button%20Seen\\\\\\\"|\\\\\\\"Result%20Clicked\\\\\\\"\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Event\\\"}}],\\\"listeners\\\":{}}\"," +
				"\"uiStateJSON\":\"{}\",\"description\":\"\"," +
				"\"version\":1," +
				"\"kibanaSavedObjectMeta\":{" +
					"\"searchSourceJSON\":\"{\\\"index\\\":\\\"" + getIndexName() + "\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"PID:" + publisher.getPid() + "\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
				"}" +
			"}";
		
	}
}

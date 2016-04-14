package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;

/**
 * Histogram
 * Filtered by: publisher domain
 * Showing TrendiGuru events
 * 
 * @author Jeremy
 *
 */
public class EventsVisual extends Visual {

	public EventsVisual(Publisher publisher) {
		this.elasticSearchId = publisher.getEncodedName() + "-events-breakdown";
		this.title = publisher.getName() + " Events Breakdown";
	}
	
	@Override
	public String getEncodedJSON(Publisher publisher) {
		return "{" +
			"\"title\": \"" + this.title + "\"," +
			"\"visState\": \"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"histogram\\\",\\\"params\\\":{\\\"shareYAxis\\\":true,\\\"addTooltip\\\":true,\\\"addLegend\\\":true,\\\"scale\\\":\\\"linear\\\",\\\"mode\\\":\\\"stacked\\\",\\\"times\\\":[],\\\"addTimeMarker\\\":false,\\\"defaultYExtents\\\":false,\\\"setYExtents\\\":false,\\\"yAxis\\\":{}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"date_histogram\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"@timestamp\\\",\\\"interval\\\":\\\"auto\\\",\\\"customInterval\\\":\\\"2h\\\",\\\"min_doc_count\\\":1,\\\"extended_bounds\\\":{}}},{\\\"id\\\":\\\"4\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"group\\\",\\\"params\\\":{\\\"field\\\":\\\"event.raw\\\",\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}},{\\\"id\\\":\\\"5\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"referer.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() + "\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"row\\\":true}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\": \"{\\\"vis\\\":{\\\"legendOpen\\\":false}}\"," +	
			"\"description\": \"\"," +
			"\"version\": 1," +
			"\"kibanaSavedObjectMeta\": {" +
				"\"searchSourceJSON\": \"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
	}
}

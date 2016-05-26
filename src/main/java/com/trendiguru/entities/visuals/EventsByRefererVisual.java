package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

/**
 * DataTable - Each publisher has many pages so events can come from any of these pages.
 * This Visual shows which of the publisher's pages produced events.
 * 
 * @author Jeremy
 *
 */
public class EventsByRefererVisual extends Visual {

	public EventsByRefererVisual(User publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-events-by-referer";
		this.title = publisher.getName() + " Events By Referer";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		return "{" +
				"\"title\": \"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"table\\\",\\\"params\\\":{\\\"perPage\\\":10,\\\"showPartialRows\\\":false,\\\"showMeticsAtAllLevels\\\":false},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"bucket\\\",\\\"params\\\":{\\\"field\\\":\\\"publisherDomain.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() +"\\\"},\\\"size\\\":30,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"bucket\\\",\\\"params\\\":{\\\"field\\\":\\\"event.raw\\\",\\\"size\\\":10,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\"," +
			"\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
	}
}

package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;

public class AverageTimeOnSite extends Visual {

	public AverageTimeOnSite(Publisher publisher) {
		this.publisher = publisher;
		this.title = publisher.getName() + " Average Time on Site";
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		return "{" +
				"\"title\": \"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"metric\\\",\\\"params\\\":{\\\"fontSize\\\":60,\\\"handleNoResults\\\":true},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"avg\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{\\\"field\\\":\\\"duration\\\",\\\"customLabel\\\":\\\"Seconds\\\"}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\",\"description\":\"\"," +
			"\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"event: \\\\\\\"Page%20Unloaded\\\\\\\" AND publisherDomain: \\\\\\\""+ publisher.getDomain() +"\\\\\\\"\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
	}
}

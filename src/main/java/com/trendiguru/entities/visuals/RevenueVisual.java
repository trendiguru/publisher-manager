package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

/**
 * Metric using a Groovy scripted field
 * {@link https://discuss.elastic.co/t/calling-groovy-script-from-kibana/2542/3}
 * 
 * Filtered by: publisher domain
 * Showing TrendiGuru events
 * 
 * @author Jeremy
 *
 */
public class RevenueVisual extends Visual {

	public RevenueVisual(User publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-events-breakdown";
		this.title = publisher.getName() + " Revenue";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		return "{" +
				"\"title\": \"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"metric\\\",\\\"params\\\":{\\\"fontSize\\\":60,\\\"handleNoResults\\\":true},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"sum\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{\\\"field\\\":\\\"dummy_field\\\",\\\"json\\\":\\\"{ \\\\\\\"script\\\\\\\":\\\\\\\"doc['event.raw'].value == 'Result%20Clicked' ? (0.25 * 0.5) : 0\\\\\\\", \\\\\\\"lang\\\\\\\": \\\\\\\"groovy\\\\\\\" }\\\",\\\"customLabel\\\":\\\"Approximate (US $)\\\"}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\",\"description\":\"\",\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"PID:"+ publisher.getPid() + "\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
	}
}

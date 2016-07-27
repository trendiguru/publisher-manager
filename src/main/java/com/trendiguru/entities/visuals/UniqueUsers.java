package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

/**
 * unique users
 * Filtered by: publisher domain
 * 
 * @author Jeremy
 *
 */
public class UniqueUsers extends Visual {

	public UniqueUsers(User publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-unique-users";
		this.title = publisher.getName() + " Unique Users";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		
		return "{" +
			"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"metric\\\",\\\"params\\\":{\\\"fontSize\\\":60,\\\"handleNoResults\\\":true},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"cardinality\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{\\\"field\\\":\\\"userId.raw\\\",\\\"json\\\":\\\"{\\\\\\\"precision_threshold\\\\\\\": 1000}\\\",\\\"customLabel\\\":\\\"Unique Users\\\"}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\",\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"" + getIndexName() + "\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"PID:"+ publisher.getPid() +"\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
		
		//Get a single, aggregated number for the given date period
		/*
		return "{" +
			"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"metric\\\",\\\"params\\\":{\\\"fontSize\\\":60},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"cardinality\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{\\\"field\\\":\\\"userId.raw\\\",\\\"json\\\":\\\"{\\\\\\\"precision_threshold\\\\\\\": 1000}\\\",\\\"customLabel\\\":\\\"Unique Users\\\"}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\",\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\""+ publisher.getDomain() +"\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
		*/		
	}
}

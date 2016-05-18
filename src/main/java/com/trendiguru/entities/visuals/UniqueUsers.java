package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;

/**
 * unique users
 * Filtered by: publisher domain
 * 
 * @author Jeremy
 *
 */
public class UniqueUsers extends Visual {

	public UniqueUsers(Publisher publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-unique-users";
		this.title = publisher.getName() + " Unique Users";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		
		//String s = "{\"title\":\"Fashion Seoul Unique Users 2\",\"visState\":\"{\\\"title\\\":\\\"Fashion Seoul Unique Users\\\",\\\"type\\\":\\\"metric\\\",\\\"params\\\":{\\\"fontSize\\\":60,\\\"handleNoResults\\\":true},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"cardinality\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{\\\"field\\\":\\\"userId.raw\\\",\\\"json\\\":\\\"{\\\\\\\"precision_threshold\\\\\\\": 1000}\\\",\\\"customLabel\\\":\\\"Unique Users\\\"}}],\\\"listeners\\\":{}}\",\"uiStateJSON\":\"{}\",\"description\":\"\",\"version\":1,\"kibanaSavedObjectMeta\":{\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"fashionseoul.com\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"}}";
		
		
		//Get a single, aggregated number for the given date period
		return "{" +
			"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"metric\\\",\\\"params\\\":{\\\"fontSize\\\":60},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"cardinality\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{\\\"field\\\":\\\"userId.raw\\\",\\\"json\\\":\\\"{\\\\\\\"precision_threshold\\\\\\\": 1000}\\\",\\\"customLabel\\\":\\\"Unique Users\\\"}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\",\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\""+ publisher.getDomain() +"\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
		
		//get a bar chart of unique users per day
		/*
		return "{" +
			"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"histogram\\\",\\\"params\\\":{\\\"addLegend\\\":true,\\\"addTimeMarker\\\":false,\\\"addTooltip\\\":true,\\\"defaultYExtents\\\":false,\\\"mode\\\":\\\"stacked\\\",\\\"scale\\\":\\\"linear\\\",\\\"setYExtents\\\":false,\\\"shareYAxis\\\":true,\\\"times\\\":[],\\\"yAxis\\\":{}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"cardinality\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{\\\"field\\\":\\\"userId.raw\\\",\\\"json\\\":\\\"{\\\\\\\"precision_threshold\\\\\\\": 1000}\\\"}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"date_histogram\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"@timestamp\\\",\\\"interval\\\":\\\"d\\\",\\\"customInterval\\\":\\\"2h\\\",\\\"min_doc_count\\\":1,\\\"extended_bounds\\\":{}}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"group\\\",\\\"params\\\":{\\\"field\\\":\\\"publisherDomain.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() +"\\\"},\\\"size\\\":20,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\",\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
		*/
	}
}

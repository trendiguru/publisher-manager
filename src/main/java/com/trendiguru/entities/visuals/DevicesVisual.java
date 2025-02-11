package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

/**
 * Pie Chart
 * Filtered by: publisher domain
 * Showing device OS
 * 
 * @author Jeremy
 *
 */
public class DevicesVisual extends Visual {

	public DevicesVisual(User publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-devices";
		this.title = publisher.getName() + " Devices";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		
		return "{" +
				"\"title\":\"" + this.title + "\"," +
				"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"pie\\\",\\\"params\\\":{\\\"shareYAxis\\\":true,\\\"addTooltip\\\":true,\\\"addLegend\\\":true,\\\"isDonut\\\":false},\\\"aggs\\\":[" +
					"{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}}," +
					"{\\\"id\\\":\\\"5\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"PID.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getPid() +"\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"PID\\\"}}," +
					"{\\\"id\\\":\\\"4\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"publisherDomain.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\"\\\"},\\\"size\\\":1,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Domain\\\"}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"ua_os.raw\\\",\\\"size\\\":15,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"OS\\\"}}" +
				"],\\\"listeners\\\":{}}\"," +
				"\"uiStateJSON\":\"{}\"," +
				"\"description\":\"\",\"version\":1," +
				"\"kibanaSavedObjectMeta\":{" +
					"\"searchSourceJSON\":\"{\\\"index\\\":\\\"" + getIndexName() + "\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
				"}" +
			"}";
		
		/*
		return "{" +
			"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"pie\\\",\\\"params\\\":{\\\"shareYAxis\\\":true,\\\"addTooltip\\\":true,\\\"addLegend\\\":true,\\\"isDonut\\\":false},\\\"aggs\\\":[" +
					"{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}}," +
					
					
					"{\\\"id\\\":\\\"4\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"publisherDomain.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() +"\\\"},\\\"size\\\":1,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}}," +
					"{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"ua_os.raw\\\",\\\"size\\\":15,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}}" +
			"],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\",\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
		*/
	}
}

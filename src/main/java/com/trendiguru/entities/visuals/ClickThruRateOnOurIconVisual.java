package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;

/**
 * Calculate CTR on our icon in the publisher's page out of all events for the given publisher's site.
 * 
 * TimeLion plugin for Kibana provides a query language that allows calculations on aggregations!
 * 
 * THe JSON below has a type of "timelion".  The plugin provides a tutorial within its Kibana tab to help figure out their query language!
 * 
 * @author Jeremy
 *
 */
public class ClickThruRateOnOurIconVisual extends Visual {

	String publisherDomainWithoutProtocol;
	
	public ClickThruRateOnOurIconVisual(Publisher publisher) {
		this.publisher = publisher;
		this.elasticSearchId = publisher.getEncodedName() + "-click-thru-rate-our-icon";
		this.title = publisher.getName() + " Click Thru Rate On Our Icon";
		
		/* TimeLion breaks if the referer has "http://" or "/" inside it! */
		//this.publisherDomainWithoutProtocol = publisher.getDomain().replace("http://", "").replace("/", "");
	}
	
	@Override
	public String getEncodedJSON() {
		
		return "{" +
				"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"timelion\\\",\\\"params\\\":{\\\"expression\\\":\\\".es('publisherDomain:"+ publisher.getDomain() + " AND event:\\\\\\\"Trendi%20Button%20Clicked\\\\\\\"').divide(.es('publisherDomain:"+ publisher.getDomain() + " AND event:*')).multiply(100).bars()\\\",\\\"interval\\\":\\\"1d\\\"},\\\"aggs\\\":[],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\"," +
			"\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
	}
}

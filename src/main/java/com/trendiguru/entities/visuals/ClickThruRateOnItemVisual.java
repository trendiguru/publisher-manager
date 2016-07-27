package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

/**
 * Calculate CTR on an item/clothing in our iframe/app.
 * 
 * TimeLion plugin for Kibana provides a query language that allows calculations on aggregations!
 * 
 * THe JSON below has a type of "timelion".  The plugin provides a tutorial within its Kibana tab to help figure out their query language!
 *  
 * @author Jeremy
 *
 */
public class ClickThruRateOnItemVisual extends Visual {

	//TODO - add index
	
	public ClickThruRateOnItemVisual(User publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-click-thru-rate-item";
		this.title = publisher.getName() + " Click Thru Rate On Items";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		
		return "{" +
				"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"timelion\\\",\\\"params\\\":{\\\"expression\\\":\\\".es('PID:"+ publisher.getPid() + " AND event:\\\\\\\"Result%20Clicked\\\\\\\"').divide(.es('PID:"+ publisher.getPid() + " AND event:*')).multiply(100).lines().label('Rate as %').legend('ne')\\\",\\\"interval\\\":\\\"1d\\\"},\\\"aggs\\\":[],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\"," +
			"\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
	}
}

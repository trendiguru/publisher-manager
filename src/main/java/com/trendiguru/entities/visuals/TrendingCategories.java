package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

public class TrendingCategories extends Visual {

	public TrendingCategories(User publisher) {
		this.publisher = publisher;
		this.title = publisher.getName() + " Top 20 Trending Categories";
		//this.elasticSearchId = publisher.getEncodedName() + "-trending-categories";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");	
	}
	
	@Override
	public String getEncodedJSON() {
		
		return "{" +
				"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"histogram\\\",\\\"params\\\":{\\\"shareYAxis\\\":true,\\\"addTooltip\\\":true,\\\"addLegend\\\":false,\\\"scale\\\":\\\"linear\\\",\\\"mode\\\":\\\"stacked\\\",\\\"times\\\":[],\\\"addTimeMarker\\\":false,\\\"defaultYExtents\\\":false,\\\"setYExtents\\\":false,\\\"yAxis\\\":{}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"catName.raw\\\",\\\"size\\\":20,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Category\\\"}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"group\\\",\\\"params\\\":{\\\"field\\\":\\\"PID.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getPid() +"\\\"},\\\"size\\\":10,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Domain\\\"}}],\\\"listeners\\\":{}}\",\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\"," +
			"\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"" + getIndexName() + "\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
	}
}

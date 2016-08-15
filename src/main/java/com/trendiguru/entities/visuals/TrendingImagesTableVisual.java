package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

public class TrendingImagesTableVisual extends Visual {

	public TrendingImagesTableVisual(User publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-trending-images";
		this.title = publisher.getName() + " Top 20 Trending Clicked Images";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
				
		//table json 10th july 2016
		return "{" +
				"\"title\":\"" + this.title + "\"," +
				"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"table\\\",\\\"params\\\":{\\\"perPage\\\":10,\\\"showPartialRows\\\":false,\\\"showMeticsAtAllLevels\\\":false},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"bucket\\\",\\\"params\\\":{\\\"field\\\":\\\"publisherDomain.raw\\\",\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Domain\\\"}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"bucket\\\",\\\"params\\\":{\\\"field\\\":\\\"imageURL.raw\\\",\\\"size\\\":20,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Image\\\"}}],\\\"listeners\\\":{}}\",\"uiStateJSON\":\"{}\"," +
				"\"description\":\"\"," +
				"\"version\":1," +
				"\"kibanaSavedObjectMeta\":{" +
					"\"searchSourceJSON\":\"{\\\"index\\\":\\\"" + getIndexName() + "\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"PID:" + publisher.getPid() + " AND event:\\\\\\\"Trendi%20Button%20Clicked\\\\\\\"\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
				"}" +
			"}";
	}
}

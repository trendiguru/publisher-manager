package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

public class TrendingImagesVisual extends Visual {

	public TrendingImagesVisual(User publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-trending-images";
		this.title = publisher.getName() + " Top 20 Trending Images";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		
		//String s = "{\"title\":\"Fashion Seoul Top 20 Trending Images 2\",\"visState\":\"{\\\"title\\\":\\\"Fashion Seoul Top 20 Trending Images\\\",\\\"type\\\":\\\"histogram\\\",\\\"params\\\":{\\\"addLegend\\\":true,\\\"addTimeMarker\\\":false,\\\"addTooltip\\\":true,\\\"defaultYExtents\\\":false,\\\"mode\\\":\\\"stacked\\\",\\\"scale\\\":\\\"linear\\\",\\\"setYExtents\\\":false,\\\"shareYAxis\\\":true,\\\"times\\\":[],\\\"yAxis\\\":{}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"date_histogram\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"@timestamp\\\",\\\"interval\\\":\\\"auto\\\",\\\"customInterval\\\":\\\"2h\\\",\\\"min_doc_count\\\":1,\\\"extended_bounds\\\":{},\\\"customLabel\\\":\\\"Time\\\"}},{\\\"id\\\":\\\"5\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"publisherDomain.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\"fashionseoul.com\\\"},\\\"size\\\":20,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Domain\\\",\\\"row\\\":true}},{\\\"id\\\":\\\"4\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"group\\\",\\\"params\\\":{\\\"field\\\":\\\"imageURL.raw\\\",\\\"size\\\":20,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Image\\\"}}],\\\"listeners\\\":{}}\",\"uiStateJSON\":\"{\\\"vis\\\":{\\\"legendOpen\\\":false}}\",\"description\":\"\",\"version\":1,\"kibanaSavedObjectMeta\":{\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}},\\\"filter\\\":[]}\"}}";
		
		return "{" +
			"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"histogram\\\",\\\"params\\\":{\\\"addLegend\\\":true,\\\"addTimeMarker\\\":false,\\\"addTooltip\\\":true,\\\"defaultYExtents\\\":false,\\\"mode\\\":\\\"stacked\\\",\\\"scale\\\":\\\"linear\\\",\\\"setYExtents\\\":false,\\\"shareYAxis\\\":true,\\\"times\\\":[],\\\"yAxis\\\":{}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"date_histogram\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"@timestamp\\\",\\\"interval\\\":\\\"auto\\\",\\\"customInterval\\\":\\\"2h\\\",\\\"min_doc_count\\\":1,\\\"extended_bounds\\\":{},\\\"customLabel\\\":\\\"Time\\\"}},{\\\"id\\\":\\\"5\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"publisherDomain.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() +"\\\"},\\\"size\\\":20,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Domain\\\",\\\"row\\\":true}},{\\\"id\\\":\\\"4\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"group\\\",\\\"params\\\":{\\\"field\\\":\\\"imageURL.raw\\\",\\\"size\\\":20,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Image\\\"}}],\\\"listeners\\\":{}}\",\"uiStateJSON\":\"{\\\"vis\\\":{\\\"legendOpen\\\":false}}\"," +
			"\"description\":\"\"," +
			"\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
	}
}

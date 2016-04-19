package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;

public class TrendingImagesVisual extends Visual {

	public TrendingImagesVisual(Publisher publisher) {
		this.publisher = publisher;
		this.elasticSearchId = publisher.getEncodedName() + "-trending-images";
		this.title = publisher.getName() + " Top 20 Trending Images";
	}
	
	@Override
	public String getEncodedJSON() {
		return "{" +
			"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"histogram\\\",\\\"params\\\":{\\\"addLegend\\\":true,\\\"addTimeMarker\\\":false,\\\"addTooltip\\\":true,\\\"defaultYExtents\\\":false,\\\"mode\\\":\\\"stacked\\\",\\\"scale\\\":\\\"linear\\\",\\\"setYExtents\\\":false,\\\"shareYAxis\\\":true,\\\"times\\\":[],\\\"yAxis\\\":{}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"date_histogram\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"@timestamp\\\",\\\"interval\\\":\\\"auto\\\",\\\"customInterval\\\":\\\"2h\\\",\\\"min_doc_count\\\":1,\\\"extended_bounds\\\":{}}},{\\\"id\\\":\\\"5\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"refererDomain.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() +"\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"row\\\":true}},{\\\"id\\\":\\\"4\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"group\\\",\\\"params\\\":{\\\"field\\\":\\\"imageURL.raw\\\",\\\"size\\\":20,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}}],\\\"listeners\\\":{}}\",\"uiStateJSON\":\"{\\\"vis\\\":{\\\"legendOpen\\\":false}}\"," +
			"\"description\":\"\"," +
			"\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
	}
}

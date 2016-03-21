package com.trendiguru.elasticsearch;

import com.trendiguru.entities.Publisher;

public class VisualizationManager extends ElasticSearchManager {
	
	public VisualizationManager() {
	}
	
	public String getESEntityUrl(String visualName) {
		//String url = "http://localhost:9000/elasticsearch/.kibana/visualization/" + visualName + "?op_type=create";
		return "http://localhost:9000/elasticsearch/.kibana/visualization/" + visualName + "?op_type=create";
	}
	
	public void addDataTable(Publisher publisher) {
		String visualEncodedJSON = jsonForDataTable(publisher);
    	send(publisher, visualEncodedJSON, "Data-Table-for-" + publisher.getName());
	}
	
	public void addHistoGram(Publisher publisher) {
		String visualEncodedJSON = jsonForHistogram(publisher);
    	send(publisher, visualEncodedJSON, "Histogram-for-" + publisher.getName());
	}
	
	public String jsonForDataTable(Publisher publisher) {
    	String json = "{" +
				"\"title\": \"Data Table for Publisher " + publisher.getName() + "\"," +
				"\"visState\": \"{\\\"title\\\":\\\"Data Table for Publisher " + publisher.getName() + "\\\",\\\"type\\\":\\\"table\\\",\\\"params\\\":{\\\"perPage\\\":10,\\\"showPartialRows\\\":false,\\\"showMeticsAtAllLevels\\\":false},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"bucket\\\",\\\"params\\\":{\\\"field\\\":\\\"event.raw\\\",\\\"size\\\":10,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"bucket\\\",\\\"params\\\":{\\\"field\\\":\\\"referer.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() + "\\\"},\\\"size\\\":10,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}}],\\\"listeners\\\":{}}\"," +
				"\"uiStateJSON\": \"{}\"," +
				"\"description\": \"\"," +
				"\"version\": 1," +
				"\"kibanaSavedObjectMeta\": {" +
					"\"searchSourceJSON\": \"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}},\\\"filter\\\":[]}\"" +
				"}" +
			"}";
    	
    	return json;
    }
    
	public String jsonForHistogram(Publisher publisher) {
    	String json = "{" +
    			"\"title\": \"Histogram for Publisher " + publisher.getName() + "\"," +
				"\"visState\": \"{\\\"title\\\":\\\"Histogram for Publisher " + publisher.getName() + "\\\",\\\"type\\\":\\\"histogram\\\",\\\"params\\\":{\\\"shareYAxis\\\":true,\\\"addTooltip\\\":true,\\\"addLegend\\\":true,\\\"scale\\\":\\\"linear\\\",\\\"mode\\\":\\\"stacked\\\",\\\"times\\\":[],\\\"addTimeMarker\\\":false,\\\"defaultYExtents\\\":false,\\\"setYExtents\\\":false,\\\"yAxis\\\":{}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"date_histogram\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"@timestamp\\\",\\\"interval\\\":\\\"auto\\\",\\\"customInterval\\\":\\\"2h\\\",\\\"min_doc_count\\\":1,\\\"extended_bounds\\\":{}}},{\\\"id\\\":\\\"4\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"group\\\",\\\"params\\\":{\\\"field\\\":\\\"event.raw\\\",\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}},{\\\"id\\\":\\\"5\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"referer.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() + "\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"row\\\":true}}],\\\"listeners\\\":{}}\"," +
				"\"uiStateJSON\": \"{\\\"vis\\\":{\\\"legendOpen\\\":false}}\"," +	
    			"\"description\": \"\"," +
    			"\"version\": 1," +
    			"\"kibanaSavedObjectMeta\": {" +
    				"\"searchSourceJSON\": \"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}},\\\"filter\\\":[]}\"" +
    			"}" +
    		"}";
    	
    	return json;
    }
}

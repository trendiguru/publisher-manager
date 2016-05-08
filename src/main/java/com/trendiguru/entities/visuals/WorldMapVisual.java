package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;

/**
 * Map
 * Filtered by publisher domain
 * Showing total events for this domain in the world
 * 
 * @author Jeremy
 *
 */
public class WorldMapVisual extends Visual {

	public WorldMapVisual(Publisher publisher) {
		this.publisher = publisher;
		this.elasticSearchId = publisher.getEncodedName() + "-world-map";
		this.title = publisher.getName() + " World Map";
	}
	
	@Override
	public String getEncodedJSON() {
		
		//String s = "{\"title\":\"Fashion Seoul World Map 2\",\"visState\":\"{\\\"title\\\":\\\"Fashion Seoul World Map\\\",\\\"type\\\":\\\"tile_map\\\",\\\"params\\\":{\\\"mapType\\\":\\\"Scaled Circle Markers\\\",\\\"isDesaturated\\\":true,\\\"addTooltip\\\":true,\\\"heatMaxZoom\\\":16,\\\"heatMinOpacity\\\":0.1,\\\"heatRadius\\\":25,\\\"heatBlur\\\":15,\\\"heatNormalizeData\\\":true,\\\"wms\\\":{\\\"enabled\\\":false,\\\"url\\\":\\\"https:\/\/basemap.nationalmap.gov\/arcgis\/services\/USGSTopo\/MapServer\/WMSServer\\\",\\\"options\\\":{\\\"version\\\":\\\"1.3.0\\\",\\\"layers\\\":\\\"0\\\",\\\"format\\\":\\\"image\/png\\\",\\\"transparent\\\":true,\\\"attribution\\\":\\\"Maps provided by USGS\\\",\\\"styles\\\":\\\"\\\"}}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"geohash_grid\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"geoip.location\\\",\\\"autoPrecision\\\":true,\\\"mapZoom\\\":2,\\\"mapCenter\\\":[14.944784875088372,4.921875],\\\"precision\\\":2,\\\"customLabel\\\":\\\"Geo Hash\\\"}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"publisherDomain.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\"fashionseoul.com\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Domain\\\",\\\"row\\\":true}}],\\\"listeners\\\":{}}\",\"uiStateJSON\":\"{\\\"spy\\\":{\\\"mode\\\":{\\\"name\\\":\\\"table\\\",\\\"fill\\\":false}}}\",\"description\":\"\",\"version\":1,\"kibanaSavedObjectMeta\":{\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"}}";
		
		return "{" +
			"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"tile_map\\\",\\\"params\\\":{\\\"mapType\\\":\\\"Scaled Circle Markers\\\",\\\"isDesaturated\\\":true,\\\"addTooltip\\\":true,\\\"heatMaxZoom\\\":16,\\\"heatMinOpacity\\\":0.1,\\\"heatRadius\\\":25,\\\"heatBlur\\\":15,\\\"heatNormalizeData\\\":true,\\\"wms\\\":{\\\"enabled\\\":false,\\\"url\\\":\\\"https:\\/\\/basemap.nationalmap.gov\\/arcgis\\/services\\/USGSTopo\\/MapServer\\/WMSServer\\\",\\\"options\\\":{\\\"version\\\":\\\"1.3.0\\\",\\\"layers\\\":\\\"0\\\",\\\"format\\\":\\\"image\\/png\\\",\\\"transparent\\\":true,\\\"attribution\\\":\\\"Maps provided by USGS\\\",\\\"styles\\\":\\\"\\\"}}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"geohash_grid\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"geoip.location\\\",\\\"autoPrecision\\\":true,\\\"precision\\\":2,\\\"customLabel\\\":\\\"Geo Hash\\\"}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"publisherDomain.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() +"\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Domain\\\",\\\"row\\\":true}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\"," +
			"\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
		
		
		/*
		return "{" +
			"\"title\": \"" + publisher.getName() + " - Click Thru Rate\"," +
			"\"visState\": \"{\\\"title\\\":\\\"Histogram for Publisher " + publisher.getName() + "\\\",\\\"type\\\":\\\"histogram\\\",\\\"params\\\":{\\\"shareYAxis\\\":true,\\\"addTooltip\\\":true,\\\"addLegend\\\":true,\\\"scale\\\":\\\"linear\\\",\\\"mode\\\":\\\"stacked\\\",\\\"times\\\":[],\\\"addTimeMarker\\\":false,\\\"defaultYExtents\\\":false,\\\"setYExtents\\\":false,\\\"yAxis\\\":{}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"date_histogram\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"@timestamp\\\",\\\"interval\\\":\\\"auto\\\",\\\"customInterval\\\":\\\"2h\\\",\\\"min_doc_count\\\":1,\\\"extended_bounds\\\":{}}},{\\\"id\\\":\\\"4\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"group\\\",\\\"params\\\":{\\\"field\\\":\\\"event.raw\\\",\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\"}},{\\\"id\\\":\\\"5\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"referer.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getDomain() + "\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"row\\\":true}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\": \"{\\\"vis\\\":{\\\"legendOpen\\\":false}}\"," +	
			"\"description\": \"\"," +
			"\"version\": 1," +
			"\"kibanaSavedObjectMeta\": {" +
				"\"searchSourceJSON\": \"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
		*/
	}
}

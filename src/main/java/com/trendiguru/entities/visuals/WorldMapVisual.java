package com.trendiguru.entities.visuals;

import com.trendiguru.entities.User;

/**
 * Map
 * Filtered by publisher domain
 * Showing total events for this domain in the world
 * 
 * @author Jeremy
 *
 */
public class WorldMapVisual extends Visual {

	public WorldMapVisual(User publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-world-map";
		this.title = publisher.getName() + " World Map";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		
		return "{" +
				"\"title\":\"" + this.title + "\"," +
				"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"tile_map\\\",\\\"params\\\":{\\\"mapType\\\":\\\"Scaled Circle Markers\\\",\\\"isDesaturated\\\":true,\\\"addTooltip\\\":true,\\\"heatMaxZoom\\\":16,\\\"heatMinOpacity\\\":0.1,\\\"heatRadius\\\":25,\\\"heatBlur\\\":15,\\\"heatNormalizeData\\\":true,\\\"wms\\\":{\\\"enabled\\\":true,\\\"url\\\":\\\"http:\\/\\/ows-tile.terrestris.de\\/osm-basemap\\/service?\\\",\\\"options\\\":{\\\"version\\\":\\\"1.1.1\\\",\\\"layers\\\":\\\"OSM-WMS\\\",\\\"format\\\":\\\"image\\/jpeg\\\",\\\"transparent\\\":true,\\\"attribution\\\":\\\"\\\",\\\"styles\\\":\\\"\\\"}}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"geohash_grid\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"geoip.location\\\",\\\"autoPrecision\\\":true,\\\"mapZoom\\\":2,\\\"mapCenter\\\":[15.114552871944102,4.921875],\\\"precision\\\":2,\\\"customLabel\\\":\\\"Geo Hash\\\"}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"PID.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getPid() +"\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Domain\\\",\\\"row\\\":true}}],\\\"listeners\\\":{}}\"," +
				"\"uiStateJSON\":\"{}\"," +
				"\"description\":\"\"," +
				"\"version\":1," +
				"\"kibanaSavedObjectMeta\":{" +
					"\"searchSourceJSON\":\"{\\\"index\\\":\\\"" + getIndexName() + "\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"PID:" + publisher.getPid() + "\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
				"}" +
			"}";
		/*
		return "{" +
			"\"title\":\"" + this.title + "\"," +
			"\"visState\":\"{\\\"title\\\":\\\"" + this.title + "\\\",\\\"type\\\":\\\"tile_map\\\",\\\"params\\\":{\\\"mapType\\\":\\\"Scaled Circle Markers\\\",\\\"isDesaturated\\\":true,\\\"addTooltip\\\":true,\\\"heatMaxZoom\\\":16,\\\"heatMinOpacity\\\":0.1,\\\"heatRadius\\\":25,\\\"heatBlur\\\":15,\\\"heatNormalizeData\\\":true,\\\"wms\\\":{\\\"enabled\\\":false,\\\"url\\\":\\\"https:\\/\\/basemap.nationalmap.gov\\/arcgis\\/services\\/USGSTopo\\/MapServer\\/WMSServer\\\",\\\"options\\\":{\\\"version\\\":\\\"1.3.0\\\",\\\"layers\\\":\\\"0\\\",\\\"format\\\":\\\"image\\/png\\\",\\\"transparent\\\":true,\\\"attribution\\\":\\\"Maps provided by USGS\\\",\\\"styles\\\":\\\"\\\"}}},\\\"aggs\\\":[{\\\"id\\\":\\\"1\\\",\\\"type\\\":\\\"count\\\",\\\"schema\\\":\\\"metric\\\",\\\"params\\\":{}},{\\\"id\\\":\\\"2\\\",\\\"type\\\":\\\"geohash_grid\\\",\\\"schema\\\":\\\"segment\\\",\\\"params\\\":{\\\"field\\\":\\\"geoip.location\\\",\\\"autoPrecision\\\":true,\\\"precision\\\":2,\\\"customLabel\\\":\\\"Geo Hash\\\"}},{\\\"id\\\":\\\"3\\\",\\\"type\\\":\\\"terms\\\",\\\"schema\\\":\\\"split\\\",\\\"params\\\":{\\\"field\\\":\\\"PID.raw\\\",\\\"include\\\":{\\\"pattern\\\":\\\""+ publisher.getPid() +"\\\"},\\\"size\\\":5,\\\"order\\\":\\\"desc\\\",\\\"orderBy\\\":\\\"1\\\",\\\"customLabel\\\":\\\"Domain\\\",\\\"row\\\":true}}],\\\"listeners\\\":{}}\"," +
			"\"uiStateJSON\":\"{}\"," +
			"\"description\":\"\"," +
			"\"version\":1," +
			"\"kibanaSavedObjectMeta\":{" +
				"\"searchSourceJSON\":\"{\\\"index\\\":\\\"logstash-*\\\",\\\"query\\\":{\\\"query_string\\\":{\\\"query\\\":\\\"*\\\",\\\"analyze_wildcard\\\":true}},\\\"filter\\\":[]}\"" +
			"}" +
		"}";
		*/
	}
}

package com.trendiguru.entities.dashboard;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendiguru.config.ConfigManager;
import com.trendiguru.infra.HttpUtil;
import com.trendiguru.infra.JsonFactory;

public class DashboardFilterData {

	private static Logger log = Logger.getLogger(DashboardFilterData.class);
	private ConfigManager configManager = ConfigManager.getInstance();
	public final static DashboardFilterData INSTANCE = new DashboardFilterData();
	public final static String[] IGNORE_FIELDS_ARRAY = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","n","r","t","dummy_field","_score","_type","_id","title","token","libs","refererDomain","@version","_index","agent","@timestamp","status","req_time","ui","path","geoip.region_name","PID","closed_after","remoteUser","publisherdomain","logdata","referdomain","ua_build","host","pageUrl","ver","ua_minor","ua_major","type","catName","winwidth","geoip.postal_code","winheight","userid","overlayType","ua_os_major","ua_os_minor","ua_os_name","refererdomain","tutoiral","request","logdate","geoip.continent_code","clientip","item_id","geoip.real_region_name","geoip.timezone","pid","ua_patch","geoip.country_code2","tutorial","geoip.country_code3","referer","geoip.city_name","clickUrl"};
	public final static Set<String> IGNORE_FIELDS_SET = new HashSet<String>(Arrays.asList(IGNORE_FIELDS_ARRAY));
	
	
	private DashboardFilterData() {
	}
	
	public static DashboardFilterData getInstance() {
		return INSTANCE;
	}
	
	public List<String> getPublisherDomainsPerPID(String PID) {
		return getFieldValues(getDomainsPerPIDJSON(PID));
	}
	
	public List<String> getPublisherCountriesPerPID(String PID) {
		return getFieldValues(getCountriesPerPIDJSON(PID));
	}
	
	/**
	 * Send query to ElasticSearch via Sense plugin
	 * 
	 * @param queryPath eg _search?filter_path=hits.hits._source.fields&pretty
	 * @param json eg {
			  "query" : {
			    "match" : {
			      "_id": "logstash-*"
			    }
			  }
			}
	 * @return eg al fields for the logstash index
	 */
	public String runElasticSearchQuery(String queryPath, String json) {
		String queryString = "uri=http%3A%2F%2F" + URLEncoder.encode(configManager.getSenseDomain()) + queryPath;
		String url = "http://" + configManager.getKibanaDomain() + "/api/sense/proxy?" + queryString;
		String result = HttpUtil.httpPostToKibana(url, json);
		return result;
	}
	
	public Set<String> getAllFilterFieldNamesForOurIndex() {
		
		String json = "{" +
				  "\"query\" : {" +
				    "\"match\" : {" +
				      "\"_id\": \"logstash-*\"" +
				    "}" +
				  "}" +
				"}";
		
		String result = runElasticSearchQuery("%2F_search%3Ffilter_path%3Dhits.hits._source.fields%26pretty", json);
				
		Set<String> allowedFieldsSet = new HashSet<String>();
		
		JsonFactory jsonFactory = JsonFactory.getInstance();
		ObjectMapper mapper = jsonFactory.getMapper();
		
		try {
			JsonNode configJson = mapper.readTree(result.toString());
			
			if (configJson.get("status") == null) {
				String escapedFields =  configJson.get("hits").get("hits").get(0).get("_source").get("fields").asText();
				//escapedFields = StringEscapeUtils.unescapeJava(escapedFields);
				
				JsonNode filterValueJson = mapper.readTree(StringEscapeUtils.unescapeJava(escapedFields));
				
				Iterator<JsonNode> itr = filterValueJson.elements();
				
				
				
				while(itr.hasNext()) {
					JsonNode element = itr.next();
					String nodeValue = element.get("name").asText();
					
					boolean use = true;
					for (String valueToIgnore : IGNORE_FIELDS_SET) {
					
						//ignore: if in list, in list with ".raw", or does not have .raw
						if (nodeValue.equals(valueToIgnore) || nodeValue.equals(valueToIgnore + ".raw") || nodeValue.indexOf(".raw") == -1 ) {
						//if (nodeValue.contains(valueToIgnore)) {
							use = false;
							break;
						}
					}
					
					if (use) {
						allowedFieldsSet.add(nodeValue);
					}
					
			    }
				
				System.out.println(allowedFieldsSet);

			} else {
				int statusCode = configJson.get("status").asInt();
				log.error(" - Sense returned an error, status code: " + statusCode);
			}
		} catch (UnsupportedEncodingException e) {
			log.fatal(e);
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			log.fatal(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.fatal(e);
			e.printStackTrace();
		}
		
		return allowedFieldsSet;
	}


	
	/**
	 * Get list of values for the given filter for the given publisher's PID.  Returned data depends on the jsonQuery param (eg get countries, get domains)
	 * 
	 * @param PID
	 * @param jsonQuery
	 * @return a List of values for the given json query
	 */
	public List<String> getFieldValues(String jsonQuery) {
		
		String result = runElasticSearchQuery("%2Flogstash-*%2F_search%3Fsearch_type%3Dcount", jsonQuery);
		
		/*
		String queryString = "uri=http%3A%2F%2F" + URLEncoder.encode(configManager.getSenseDomain()) + "%2Flogstash-*%2F_search%3Fsearch_type%3Dcount";
		String url = "http://" + configManager.getKibanaDomain() + "/api/sense/proxy?" + queryString;
		
		String result = HttpUtil.httpPostToKibana(url, jsonQuery);
		*/
		List<String> dataList = new ArrayList<String>();
		
		JsonFactory jsonFactory = JsonFactory.getInstance();
		ObjectMapper mapper = jsonFactory.getMapper();
		
		try {
			JsonNode configJson = mapper.readTree(result.toString());
			
			if (configJson.get("status") == null) {
				Iterator<JsonNode> itr = configJson.get("aggregations").get("my_filter").get("buckets").elements();
				while(itr.hasNext()) {
					JsonNode element = itr.next();
					dataList.add(element.get("key").textValue());
			        //System.out.print(element + " ");
			    }
				//log.info("Publisher PID: " + PID + ", ratio: " + ratio);
				/*
				if (response.getStatusLine().getStatusCode() == 409) {
		    		//System.out.println("document_already_exists_exception !!");
		    		log.info("document_already_exists_exception !!");
		    	}
				*/
			} else {
				int statusCode = configJson.get("status").asInt();
				log.error(" - Sense returned an error, status code: " + statusCode);
			}
		} catch (UnsupportedEncodingException e) {
			log.fatal(e);
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			log.fatal(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.fatal(e);
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	
	/**
	 * Generic method to get a filter field's values from Elastic Search
	 * Eg Get all countries for the 'geoip.country_name.raw' field.
	 * 
	 * @param PID
	 * @param field eg "publisherDomain.raw", "geoip.country_name.raw"
	 * @param size eg 250 for countries
	 * @return a list of values for the given field
	 */
	public String getFilterFieldValuesPerPIDJSON(String PID, String field, int size) {
		return "{" +
			"\"size\": 0," +
			"\"query\": {" +
				"\"filtered\": {" +
					"\"query\": {" +
						"\"query_string\": {" +
							"\"query\": \"PID:" + PID + "\"," +
							"\"analyze_wildcard\": true" +
						"}" +
					"}" +
				"}" +
			"}," +
			"\"aggs\": {" +
				"\"my_filter\": {" +
					"\"terms\": {" +
						"\"field\": \"" + field + "\"," +
						"\"size\": " + size + "," +
						"\"order\": {" +
							//"\"_count\": \"desc\"" +
							"\"_term\": \"asc\"" +
						"}" +
					"}" +
				"}" +
			"}" +
		"}";
	}
	
	public String getDomainsPerPIDJSON(String PID) {
		return "{" +
			"\"size\": 0," +
			"\"query\": {" +
				"\"filtered\": {" +
					"\"query\": {" +
						"\"query_string\": {" +
							"\"query\": \"PID:" + PID + "\"," +
							"\"analyze_wildcard\": true" +
						"}" +
					"}" +
				"}" +
			"}," +
			"\"aggs\": {" +
				"\"my_filter\": {" +
					"\"terms\": {" +
						"\"field\": \"publisherDomain.raw\"," +
						"\"size\": 250," +
						"\"order\": {" +
							//"\"_count\": \"desc\"" +
							"\"_term\": \"asc\"" +
						"}" +
					"}" +
				"}" +
			"}" +
		"}";
	}
	
	public String getCountriesPerPIDJSON(String PID) {
		return "{" +
			"\"size\": 0," +
			"\"query\": {" +
				"\"filtered\": {" +
					"\"query\": {" +
						"\"query_string\": {" +
							"\"query\": \"PID:" + PID + "\"," +
							"\"analyze_wildcard\": true" +
						"}" +
					"}" +
				"}" +
			"}," +
			"\"aggs\": {" +
				"\"my_filter\": {" +
					"\"terms\": {" +
						"\"field\": \"geoip.country_name.raw\"," +
						"\"size\": 250," +
						"\"order\": {" +
							//"\"_count\": \"desc\"" +
							"\"_term\": \"asc\"" +
						"}" +
					"}" +
				"}" +
			"}" +
		"}";
	}
}

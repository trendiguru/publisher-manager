package com.trendiguru.entities.dashboard;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	private DashboardFilterData() {
	}
	
	public static DashboardFilterData getInstance() {
		return INSTANCE;
	}
	
	public List<String> getPublisherDomainsPerPID(String PID) {
		return getDataPerPID(PID, getDomainsPerPIDJSON(PID));
	}
	
	public List<String> getPublisherCountriesPerPID(String PID) {
		return getDataPerPID(PID, getCountriesPerPIDJSON(PID));
	}
	
	/**
	 * Get list of data for the given publisher's PID.  Returned data depends on the jsonQuery param (eg get countries, get domains)
	 * 
	 * @param PID
	 * @param jsonQuery
	 * @return
	 */
	public List<String> getDataPerPID(String PID, String jsonQuery) {
		
		String queryString = "uri=http%3A%2F%2F" + URLEncoder.encode(configManager.getSenseDomain()) + "%2Flogstash-*%2F_search%3Fsearch_type%3Dcount";
		String url = "http://" + configManager.getKibanaDomain() + "/api/sense/proxy?" + queryString;
		
		String result = HttpUtil.httpPostToKibana(url, jsonQuery);
		
		List<String> dataList = new ArrayList<String>();
		
		JsonFactory jsonFactory = JsonFactory.getInstance();
		ObjectMapper mapper = jsonFactory.getMapper();
		
		try {
			JsonNode configJson = mapper.readTree(result.toString());
			
			if (configJson.get("status") == null) {
				Iterator<JsonNode> itr = configJson.get("aggregations").get("domains").get("buckets").elements();
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
				log.error("Publisher PID: " + PID + " - Sense returned an error, status code: " + statusCode);
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
				"\"domains\": {" +
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
				"\"domains\": {" +
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

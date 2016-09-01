package com.trendiguru.elasticsearch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendiguru.config.ConfigManager;
import com.trendiguru.entities.User;
import com.trendiguru.entities.visuals.Visual;
import com.trendiguru.infra.HttpUtil;
import com.trendiguru.infra.JsonFactory;
import com.trendiguru.mongodb.MorphiaManager;

public class DashboardManager extends KibanaManager {
	private static Logger log = Logger.getLogger(DashboardManager.class);
	private ConfigManager configManager = ConfigManager.getInstance();
	User publisher;
	
	public DashboardManager(User publisher) {
		this.publisher  = publisher;
	}
	
	public String getESEntityUrl(String visualId) {
		return "/elasticsearch/.kibana/dashboard/" + visualId + "?op_type=create";
	}
	
	public void addDashBoard(Set<Visual> visualsToAddSet) {
		
		/* taken form JSON produced when creating/updating a dashboard */
		String panelsJSON = "{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Our-Icon\\\",\\\"panelIndex\\\":4,\\\"row\\\":9,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Items\\\",\\\"panelIndex\\\":5,\\\"row\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Top-20-Trending-Clicked-Images\\\",\\\"panelIndex\\\":6,\\\"row\\\":12,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Unique-Users\\\",\\\"panelIndex\\\":7,\\\"row\\\":12,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":10,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Average-Time-on-Site\\\",\\\"panelIndex\\\":9,\\\"row\\\":12,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Revenue\\\",\\\"panelIndex\\\":10,\\\"row\\\":14,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Events-Breakdown\\\",\\\"panelIndex\\\":12,\\\"row\\\":1,\\\"size_x\\\":12,\\\"size_y\\\":5,\\\"type\\\":\\\"visualization\\\"}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-World-Map\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":13,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"col\\\":1,\\\"row\\\":16}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Devices\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":14,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":1,\\\"row\\\":9}," +
				"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Event-Totals\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":15,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":1,\\\"row\\\":6}";
		
		
		//1. store dashboard in ES
		//uncomment before commit!
		
		String visualEncodedJSON = jsonForDashboard(visualsToAddSet, panelsJSON);
    	add(publisher, visualEncodedJSON, publisher.getEncodedName() + "-Dashboard");
		
		
    	//2. generate bitly equivalent url for this dashboard and save it against the publisher
		String dashboardVisualLayout = getShortcutURL(panelsJSON);
		//String uniqueESShortenUrlValueForDashboard = HttpUtil.httpPostToKibana("http://" + configManager.getKibanaDomain() + "/shorten", shortcutURL);
		System.out.println("visuals layout: " + dashboardVisualLayout);
		//publisher.setDashboardId(uniqueESShortenUrlValueForDashboard);
		publisher.setDashboardVisualsLayout(dashboardVisualLayout);
		MorphiaManager.getInstance().updatePublisher(publisher);
		//sendShortcurURL(shortcutURL);
		
		
		
	}
	
	/**
	 * 
	 * @param visualsToAddSet - it's hard to have a dynamic set of visuals since their layout will vary of the no. of visuals varies! So for
	 * now, the visuals are fixed!
	 * 
	 * @return
	 */
	public String jsonForDashboard(Set<Visual> visualsToAddSet, String panelsJSON) {
		
		
		return "{" +
				"\"title\":\"" + publisher.getName() + " Dashboard\"," +
				"\"hits\":0," +
				"\"description\":\"\"," +
				"\"panelsJSON\":\"[" + panelsJSON +
				
				
					/*
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Our-Icon\\\",\\\"panelIndex\\\":4,\\\"row\\\":9,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Click-Thru-Rate-On-Items\\\",\\\"panelIndex\\\":5,\\\"row\\\":6,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Top-20-Trending-Clicked-Images\\\",\\\"panelIndex\\\":6,\\\"row\\\":12,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Unique-Users\\\",\\\"panelIndex\\\":7,\\\"row\\\":12,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":10,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Average-Time-on-Site\\\",\\\"panelIndex\\\":9,\\\"row\\\":12,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":7,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Revenue\\\",\\\"panelIndex\\\":10,\\\"row\\\":14,\\\"size_x\\\":3,\\\"size_y\\\":2,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"col\\\":1,\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Events-Breakdown\\\",\\\"panelIndex\\\":12,\\\"row\\\":1,\\\"size_x\\\":12,\\\"size_y\\\":5,\\\"type\\\":\\\"visualization\\\"}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-World-Map\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":13,\\\"size_x\\\":6,\\\"size_y\\\":4,\\\"col\\\":1,\\\"row\\\":16}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Devices\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":14,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":1,\\\"row\\\":9}," +
					"{\\\"id\\\":\\\""+ publisher.getEncodedName() +"-Event-Totals\\\",\\\"type\\\":\\\"visualization\\\",\\\"panelIndex\\\":15,\\\"size_x\\\":6,\\\"size_y\\\":3,\\\"col\\\":1,\\\"row\\\":6}" + */
				"]\",\"optionsJSON\":\"{\\\"darkTheme\\\":false}\"," +
				"\"uiStateJSON\":\"{}\"," +
				"\"version\":1," +
				"\"timeRestore\":false," +
					"\"kibanaSavedObjectMeta\":{\"searchSourceJSON\":\"{\\\"filter\\\":[{\\\"query\\\":{\\\"query_string\\\":{\\\"analyze_wildcard\\\":true,\\\"query\\\":\\\"*\\\"}}}]}\"" +
				"}" +
			"}";

    }
	
	/**
	 * Convert the panels JSON into a format that can be used in the 'shorten' url to Kibana.
	 * 
	 * @param panelsJSON
	 * @return
	 */
	public String getShortcutURL(String panelsJSON) {
		panelsJSON = panelsJSON.replaceAll("\\\\", "");
		
		StringBuilder output = new StringBuilder();
		
		JsonFactory jsonFactory = JsonFactory.getInstance();
		ObjectMapper mapper = jsonFactory.getMapper();
		
		try {
			JsonNode configJson = mapper.readTree("[" + panelsJSON + "]");
			
			
				Iterator<JsonNode> itr = configJson.elements();
				while(itr.hasNext()) {
					JsonNode element = itr.next();
					//dataList.add(element.get("key").textValue());
					output.append("(");
					output.append("col:" + element.get("col") + ",id:" + element.get("id").asText() + ",panelIndex:" + element.get("panelIndex") + ",row:" +element.get("row") + ",size_x:" + element.get("size_x") + ",size_y:" + element.get("size_y") + ",type:visualization");
					output.append(")");
					if (itr.hasNext()) {
						output.append(",");
					}
			        //System.out.println(element + " ");
			    }
				//log.info("Publisher PID: " + PID + ", ratio: " + ratio);
				/*
				if (response.getStatusLine().getStatusCode() == 409) {
		    		//System.out.println("document_already_exists_exception !!");
		    		log.info("document_already_exists_exception !!");
		    	}
				*/
				
				
				
				//System.out.println(output);
			
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
		
		if (publisher.isAdmin()) {
			return output.toString().replaceAll("Trendi-Guru-Admin", "XXXX");
		} else {
			return output.toString();
		}
		//String uri = publisher.getEncodedName() + "-Dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fw,interval:%271d%27,mode:quick,timezone:Asia%2FJerusalem,to:now%2Fw))&_a=(filters:!(),options:(darkTheme:!f),panels:!(" + output + "),query:(query_string:(analyze_wildcard:!t,query:%27*%27))";
		
		
		/*
		String url = null;
		try {
			url = "{\"url\":\"/app/kibana#/dashboard/" + publisher.getEncodedName() + "-Dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fw,interval:%271d%27,mode:quick,timezone:Asia%2FJerusalem,to:now%2Fw))&_a=(filters:!(),options:(darkTheme:!f),panels:!(" + output + "),query:(query_string:(analyze_wildcard:!t,query:%27*%27)),title:%27" + URLEncoder.encode(publisher.getName(), "UTF-8").replace("+", "%20") + "%20Dashboard%27,uiState:())\"}";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.fatal(e);
			e.printStackTrace();
		}
		
		return url;
		*/
		//POST
		//http://localhost:9001/shorten
		
		//from kibana
		//{"url":"/app/kibana#/dashboard/Jeremy-Test-Dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fw,interval:%271d%27,mode:quick,timezone:Asia%2FJerusalem,to:now%2Fw))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:7,id:Jeremy-Test-Click-Thru-Rate-On-Our-Icon,panelIndex:4,row:6,size_x:6,size_y:3,type:visualization),(col:7,id:Jeremy-Test-Click-Thru-Rate-On-Items,panelIndex:5,row:9,size_x:6,size_y:3,type:visualization),(col:1,id:Jeremy-Test-Top-20-Trending-Clicked-Images,panelIndex:6,row:12,size_x:6,size_y:4,type:visualization),(col:7,id:Jeremy-Test-Unique-Users,panelIndex:7,row:12,size_x:3,size_y:2,type:visualization),(col:10,id:Jeremy-Test-Average-Time-on-Site,panelIndex:9,row:12,size_x:3,size_y:2,type:visualization),(col:7,id:Jeremy-Test-Revenue,panelIndex:10,row:14,size_x:3,size_y:2,type:visualization),(col:1,id:Jeremy-Test-Events-Breakdown,panelIndex:12,row:1,size_x:12,size_y:5,type:visualization),(col:1,id:Jeremy-Test-World-Map,panelIndex:13,row:16,size_x:6,size_y:4,type:visualization),(col:1,id:Jeremy-Test-Devices,panelIndex:14,row:9,size_x:6,size_y:3,type:visualization),(col:1,id:Jeremy-Test-Event-Totals,panelIndex:15,row:6,size_x:6,size_y:3,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:%27*%27)),title:%27Jeremy%20Test%20Dashboard%27,uiState:())"}
		//6164bc90e568a294587bbcfaae1a65d1
		//from me
		//{"url":"/app/kibana#/dashboard/Jeremy-Test-Dashboard?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fw,interval:%271d%27,mode:quick,timezone:Asia%2FJerusalem,to:now%2Fw))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:7,id:Jeremy-Test-Click-Thru-Rate-On-Our-Icon,panelIndex:4,row:9,size_x:6,size_y:3,type:visualization),(col:7,id:Jeremy-Test-Click-Thru-Rate-On-Items,panelIndex:5,row:6,size_x:6,size_y:3,type:visualization),(col:1,id:Jeremy-Test-Top-20-Trending-Clicked-Images,panelIndex:6,row:12,size_x:6,size_y:4,type:visualization),(col:7,id:Jeremy-Test-Unique-Users,panelIndex:7,row:12,size_x:3,size_y:2,type:visualization),(col:10,id:Jeremy-Test-Average-Time-on-Site,panelIndex:9,row:12,size_x:3,size_y:2,type:visualization),(col:7,id:Jeremy-Test-Revenue,panelIndex:10,row:14,size_x:3,size_y:2,type:visualization),(col:1,id:Jeremy-Test-Events-Breakdown,panelIndex:12,row:1,size_x:12,size_y:5,type:visualization),(col:1,id:Jeremy-Test-World-Map,panelIndex:13,row:16,size_x:6,size_y:4,type:visualization),(col:1,id:Jeremy-Test-Devices,panelIndex:14,row:9,size_x:6,size_y:3,type:visualization),(col:1,id:Jeremy-Test-Event-Totals,panelIndex:15,row:6,size_x:6,size_y:3,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:%27*%27)),title:%27Jeremy%20Test%20Dashboard%27,uiState:())"}

		
		//response
		//b9fbab811981dd6a7b809fbb9fe69f86
	}
	
	/**
	 * Sending this 'shorten' url below to Kibana returns a unique value that is used to identify the shortcut url for this dashbaord.
	 * 
	 * @param shortcutlURL
	 */
	/*
	public void sendShortcurURL(String shortcutlURL) {
		System.out.println(shortcutlURL);
		String response = HttpUtil.httpPostToKibana("http://localhost:9001/shorten", shortcutlURL);
		System.out.println("shorten url: " + response);
	}
	*/
}

package com.trendiguru.struts.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendiguru.elasticsearch.DashboardManager;
import com.trendiguru.entities.Publisher;
import com.trendiguru.infra.JsonFactory;


public class PublisherAction extends SecureAction {
	private static Logger log = Logger.getLogger(PublisherAction.class);
	final String GRAPH_TEMPLATE = "{\"_id\":\"**INSERT_PUBLISHER_GRAPH_NAME_HERE**\",\"_type\":\"visualization\"}";
	final String EXPORT_TEMPLATE = "{\"docs\":[**ADD_GRAPH_TEMPLATE_ARRAY_HERE**]}";
	
	String dashboardName;
	String proxyResponse;
	String kibanaUrl;
	String token;
	
	public String exportDashboard() {
		//{"docs":[{"_id":"Histogram-for-Publisher-DigitalSpy","_type":"visualization"},{"_id":"Data-Table-for-Publisher-DigitalSpy","_type":"visualization"}]}

		DashboardManager dashboardManager = new DashboardManager();
		StringBuilder graphJSONBuilder = new StringBuilder();
		for (String graphName : getLoggedInPublisher().getGraphNameSet()) {
			graphJSONBuilder.append(GRAPH_TEMPLATE.replace("**INSERT_PUBLISHER_GRAPH_NAME_HERE**", graphName));
			graphJSONBuilder.append(",");
		}
		
		String sb = graphJSONBuilder.substring(0, graphJSONBuilder.length()-1);
		String exportTemplate = EXPORT_TEMPLATE.replace("**ADD_GRAPH_TEMPLATE_ARRAY_HERE**", sb);
		
		proxyResponse = dashboardManager.read("elasticsearch/.kibana/_mget", "POST", exportTemplate);
		return null;
	}
	
	public String dashboard() {
		/*
		kibanaDashboard = publisherManager.getKibanaDashboard(getLoggedInPublisher());
		kibanaDashboard = StringEscapeUtils.escapeHtml3(kibanaDashboard.trim());
		//kibanaDashboard = kibanaDashboard.replaceAll("\"", "\\\\\"");
		
		kibanaDashboard = kibanaDashboard.replaceAll("\\s{2,}", " ");
		log.info(kibanaDashboard);
		*/
		return "dashboard";
	}
	
	
	/**
	 * iframe src calls this with the auth token as the last part of the path
	 * 
	 * I could not find the event that signalled the iframe has loaded and the date HTML was in the DOM.  So, I edited the commons.bundle.js code to show 
	 * the html bar that contains the date selector AND hide the HTML that is not needed. I copied the relevant string that contains the HTML in order to 
	 * keep the original code.  See line 64729
	 * 
	 * @return
	 */
	public String kibanaDashboard() {
		String path = request.getServletPath();
		
		//String token = request.getParameter("token");
		log.info("token: " + token);
		
		DashboardManager dashboardManager = new DashboardManager();
		
		//TODO - get dashboard for auth-ed user
		//
		//
		Publisher publisher = getLoggedInPublisher();
		
		String publisherDashboard = "Dashboard-for-DigitalSpy";
		
		proxyResponse = dashboardManager.read("app/kibana#/dashboard/" + publisherDashboard + "?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2FM,mode:quick,to:now%2FM))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:1,id:Data-Table-for-Publisher-DigitalSpy,panelIndex:1,row:1,size_x:3,size_y:2,type:visualization),(col:4,id:Histogram-for-Publisher-DigitalSpy,panelIndex:2,row:1,size_x:3,size_y:2,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:'DigitalSpy%20Dashboard',uiState:())", "GET", null);
		
		return "html";
	}
	
	/**
	 * Receive POST requests and forward to http://localhost:9000/elasticsearch/________
	 * 
	 * commons.bundle.js has been updated to:
	 * a) attach a 'token' param to each request.
	 * b) send all requests via this server and this method. 
	 * 
	 * Search for "Jeremy" in this javascript file to find the updated code.
	 * 
	 * @return
	 */
	public String kibanaProxy() {
		String path = request.getServletPath();
		String queryString = request.getQueryString();
		
		//String m = request.getMethod();
		
		Publisher publisher = getLoggedInPublisher();
		//TODO - if <publisher name> == publisher.name - allow, otherwise block!
		
		String kibanaPath = path.replace("/private/", "") + "?";
		
		Set<String> parameterNameSet = request.getParameterMap().keySet();
		for (String paramName : parameterNameSet) {
			if (!paramName.equals("token")) {
				kibanaPath += paramName + "=" + request.getParameter(paramName) + "&";
			}
		}
		
		if (kibanaPath.endsWith("&")) {
			kibanaPath = kibanaPath.substring(0, kibanaPath.length()-1);
		}
		
		DashboardManager dashboardManager = new DashboardManager();
		
		if (request.getMethod().equals("POST")) {
			String postBody = getBody(request);
			
			if (kibanaPath.indexOf("_mget") > -1) {
				extractGraphName(postBody);
			}
			//TODO - get POST payload and check for JSON _type: "visualization", _id : "Histogram-for-Publisher-<Publisher name>"
			
			proxyResponse = dashboardManager.read(kibanaPath, "POST", postBody);
		} else {
			proxyResponse = dashboardManager.read(kibanaPath, "GET", null);
		}
		
		return "proxy";
	}
	
	private void extractGraphName(String postBody) {
		JsonFactory jsonFactory = JsonFactory.getInstance();
		ObjectMapper mapper = jsonFactory.getMapper();
		try {
			JsonNode rootJson = mapper.readTree(postBody);
			if (rootJson.get("docs").get(0).get("_type").textValue().equals("visualization")) {
				String graphName = rootJson.get("docs").get(0).get("_id").textValue();
				getLoggedInPublisher().getGraphNameSet().add(graphName);
			} else {
				log.info(postBody + " does not contain visualization json so ignoring");
			}
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getBody(HttpServletRequest request) {
		String data = null;
		StringBuilder buffer = new StringBuilder();
		try {
		    BufferedReader reader = request.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		        if (request.getServletPath().indexOf("_msearch") > -1) {
		        	//buffer.append(System.getProperty("line.separator"));
		        	buffer.append("\n");
		        }
		    }

		    data = buffer.toString();
		    return data;
		} catch (IOException e) {
			log.fatal(e);
		}
		return data;
	}

	public String getProxyResponse() {
		return proxyResponse;
	}

	public String getKibanaUrl() {
		return kibanaUrl;
	}

	public void setKibanaUrl(String kibanaUrl) {
		this.kibanaUrl = kibanaUrl;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}
	
	
}

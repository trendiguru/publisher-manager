package com.trendiguru.struts.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.trendiguru.elasticsearch.DashboardManager;
import com.trendiguru.entities.Publisher;


public class PublisherAction extends SecureAction {
	private static Logger log = Logger.getLogger(PublisherAction.class);
	String dashboardName;
	
	String proxyResponse;
	
	String kibanaUrl;
	
	String token;
	
	
	
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
	
	
	//iframe would call this but we need the long querystring in the iframe??
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
			//TODO - get POST payload and check for JSON _type: "visualization", _id : "Histogram-for-Publisher-<Publisher name>"
			
			proxyResponse = dashboardManager.read(kibanaPath, "POST", postBody);
		} else {
			proxyResponse = dashboardManager.read(kibanaPath, "GET", null);
		}
		
		
		
		
		
		
		
		//proxyResponse = dashboardManager.read(kibanaPath, HttpMethod.POST, postBody);
		
		
		//send POST/GET to http://localhost:9000/elasticsearch/________
		
		//response.setContentType("application/json");
		
		return "proxy";
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
		    //buffer = buffer.substring(0, buffer.length()-1);	//remove final line separator
		    if (request.getServletPath().indexOf("_msearch") > -1) {
		    	//data = buffer.substring(0, buffer.length() - "\n".length());	//remove final line separator
		    	data = buffer.toString();
	        } else {
	        	data = buffer.toString();
	        }
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

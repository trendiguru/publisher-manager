package com.trendiguru.struts.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendiguru.elasticsearch.DashboardManager;
import com.trendiguru.entities.RoleEnum;
import com.trendiguru.entities.User;
import com.trendiguru.infra.JsonFactory;
import com.trendiguru.mongodb.MorphiaManager;


public class PublisherAction extends SecureAction {
	private static Logger log = Logger.getLogger(PublisherAction.class);
	final String GRAPH_TEMPLATE = "{\"_id\":\"**INSERT_PUBLISHER_GRAPH_NAME_HERE**\",\"_type\":\"visualization\"}";
	final String EXPORT_TEMPLATE = "{\"docs\":[**ADD_GRAPH_TEMPLATE_ARRAY_HERE**]}";
	
	String dashboardName;
	String proxyResponse;
	String kibanaUrl;
	String token;
	List<User> allUsers;
	
	/*
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
	*/
	
	/**
	 * Called by main.js after login
	 * 
	 * @return
	 */
	public String dashboard() {
		
		if (getLoggedInUser().getRole().equals(RoleEnum.Admin)) {
			allUsers = MorphiaManager.getInstance().findAll();
			return "adminDashboard";
		} else {
			return "publisherDashboard";
		}
		
		/*
		kibanaDashboard = publisherManager.getKibanaDashboard(getLoggedInPublisher());
		kibanaDashboard = StringEscapeUtils.escapeHtml3(kibanaDashboard.trim());
		//kibanaDashboard = kibanaDashboard.replaceAll("\"", "\\\\\"");
		
		kibanaDashboard = kibanaDashboard.replaceAll("\\s{2,}", " ");
		log.info(kibanaDashboard);
		*/
		//return "dashboard";
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
		User publisher = getLoggedInUser();
		
		DashboardManager dashboardManager = new DashboardManager(publisher);
		
		//TODO - get dashboard for auth-ed user
		//
		//
		
		
		//String publisherDashboard = "Dashboard-for-DigitalSpy";
		
		proxyResponse = dashboardManager.read("app/kibana#/dashboard/" + publisher.getEncodedName() + "-dashboard" + "?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2FM,mode:quick,to:now%2FM))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:1,id:Data-Table-for-Publisher-DigitalSpy,panelIndex:1,row:1,size_x:3,size_y:2,type:visualization),(col:4,id:Histogram-for-Publisher-DigitalSpy,panelIndex:2,row:1,size_x:3,size_y:2,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:'DigitalSpy%20Dashboard',uiState:())", "GET", null);
		
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
		
		User publisher = getLoggedInUser();
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
		
		DashboardManager dashboardManager = new DashboardManager(publisher);
		
		if (request.getMethod().equals("POST")) {
			String postBody = getBody(request);
			
			if (kibanaPath.indexOf("_mget") > -1) {
				String graphName = extractGraphName(postBody);
				if (graphName == null) {
					proxyResponse = dashboardManager.read(kibanaPath, "POST", postBody);
				} else {
					boolean allow = authoriseGraphRequest(graphName);
					if (allow) {
						proxyResponse = dashboardManager.read(kibanaPath, "POST", postBody);
					}
				}
			} else {
				proxyResponse = dashboardManager.read(kibanaPath, "POST", postBody);
			}
			//TODO - get POST payload and check for JSON _type: "visualization", _id : "Histogram-for-Publisher-<Publisher name>"
			
			//proxyResponse = dashboardManager.read(kibanaPath, "POST", postBody);
		} else {
			proxyResponse = dashboardManager.read(kibanaPath, "GET", null);
		}
		
		return "proxy";
	}
	
	/**
	 * The Dashboard now has graphs created by TimeLion which is a Kibana plugin and allows calculations on aggregated results.
	 * Eg click-thru-rates
	 * 
	 * This plugin has a different path to the normal Kibana graphs and a different JSON
	 * 
	 * @return
	 */
	public String timeLionProxy() {
		String path = request.getServletPath();
		
		User publisher = getLoggedInUser();
		String kibanaPath = path.replace("/private/app/", "");
		
		DashboardManager dashboardManager = new DashboardManager(publisher);
		String postBody = getBody(request);

		String queryLabel = extractPublisherNameFromTimeLionQuery(postBody);
				
		boolean allow = authoriseTimeLionRequest(queryLabel);
		if (allow) {
			proxyResponse = dashboardManager.read(kibanaPath, "POST", postBody);
		}
		
		return "proxy";
	}
	
	/**
	 * The json for a TimeLion query contains the graph name eg
	 * [".es('refererDomain:instyle.com AND event:\"Result%20Clicked\"').divide(.es('refererDomain:instyle.com AND event:*')).multiply(100).bars()"]
	 * 
	 * So I check it contains the Publisher's name in order to decide if it should be allowed!
	 * 
	 * @param graphName
	 * @return
	 */
	private boolean authoriseTimeLionRequest(String query) {
		if (getLoggedInUser().isAdmin()) {
			log.info("Admin user logged user - allowing ALL TimeLion queries");
			return true;
		} else {
			if (query.indexOf(getLoggedInUser().getDomain()) > -1) {
				//log.info("Allowing Graph requset by publisher " + getLoggedInPublisher().getName() + " for graph: " + graphName);
				return true;
			} else {
				log.fatal("BLOCKING TimeLion query by publisher " + getLoggedInUser().getName() + " for query: " + query);
				return false;
			}
		}
	}
	
	private String extractPublisherNameFromTimeLionQuery(String postBody) {
		JsonFactory jsonFactory = JsonFactory.getInstance();
		ObjectMapper mapper = jsonFactory.getMapper();
		try {
			JsonNode rootJson = mapper.readTree(postBody);
			//example json: [".es('refererDomain:instyle.com AND event:\"Result%20Clicked\"').divide(.es('refererDomain:instyle.com AND event:*')).multiply(100).bars()"]
			//'instyle.com' is the publisher
			String timeLionQuery = rootJson.get("sheet").get(0).textValue();
			return timeLionQuery;

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * The json for a graph request contains the graph name eg "DigitalSpy-histogram-events" so I check if the graph contains the Publisher's name
	 * in order to decide if it should be allowed!
	 * 
	 * @param graphName
	 * @return
	 */
	private boolean authoriseGraphRequest(String graphName) {
		if (getLoggedInUser().isAdmin()) {
			log.info("Admin user logged user - allowing ALL graph queries");
			return true;
		} else {
			if (graphName.indexOf(getLoggedInUser().getEncodedName()) > -1) {
				//log.info("Allowing Graph requset by publisher " + getLoggedInPublisher().getName() + " for graph: " + graphName);
				return true;
			} else {
				log.fatal("BLOCKING graph request by publisher " + getLoggedInUser().getName() + " for graph: " + graphName);
				return false;
			}
		}
	}
	
	private String extractGraphName(String postBody) {
		JsonFactory jsonFactory = JsonFactory.getInstance();
		ObjectMapper mapper = jsonFactory.getMapper();
		try {
			JsonNode rootJson = mapper.readTree(postBody);
			if (rootJson.get("docs").get(0).get("_type").textValue().equals("visualization")) {
				//example json: {"docs":[{"_index":".kibana","_type":"visualization","_id":"instyle-click-thru-rate-our-icon"}]}
				//'instyle' is the publisher
				String graphName = rootJson.get("docs").get(0).get("_id").textValue();
				return graphName;
			} else {
				log.info(postBody + " does not contain visualization json so not checking for authorisation permission");
				return null;
			}
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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

	public List<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}
	
}

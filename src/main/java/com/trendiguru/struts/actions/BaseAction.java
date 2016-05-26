package com.trendiguru.struts.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.trendiguru.elasticsearch.PublisherManager;
import com.trendiguru.entities.User;
import com.trendiguru.infra.Constants;
import com.trendiguru.infra.JsonFactory;
import com.trendiguru.services.AuthenticationServices;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {
	
	private static Logger log = Logger.getLogger(BaseAction.class);
	
	public static final String ALREADY_LOGGED_IN = "alreadyLoggedIn";
	public static final String DASHBOARD = "dashboard";
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String ACCOUNT = "account";
	
	public static final String LOGGED_IN = "loggedIn";
	public static final String NOTIFICATION_MESSAGES = "notificationMessages";
	public static final String EMPTY = "empty";
		
	protected Map<String, Object> session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	
	protected PublisherManager publisherManager = PublisherManager.getInstance();
	protected AuthenticationServices authenticationServices = AuthenticationServices.getInstance();
	

	/**
	 * Convert object into JSON String
	 * 
	 * @param object - any object or collection of objects
	 * @return
	 */
	public String toJson(Object object) {
		JsonFactory jsonFactory = JsonFactory.getInstance();
		return jsonFactory.toJson(object);
	}
	
	public User getLoggedInUser() {
		return (User)session.get(Constants.LOGGED_IN_USER);
	}
	
/*	public SysAdmin getSysAdminUser() {
		return (SysAdmin)getLoggedInUser();
	}*/
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;		
	}
	
	
	public HttpSession getSession(){
		// Returns the current HttpSession associated with this request or, if there is no current session and create is true, returns a new session. 
		 // - see http://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html
		 //
		return request.getSession(true);
	}
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response){
	    this.response = response;
	}
/*
	public List<String> getCountries()
	{
		return Countries.countries;
	}
*/

	/*
	public String getUploadedImageURI() {
		return uploadedImageURI;
	}

	public void setUploadedImageURI(String uploadedImageURI) {
		this.uploadedImageURI = uploadedImageURI;
	}
*/
	
//	public String getUploadImageFilename() {
//		return uploadImageFilename;
//	}
//
//	public void setUploadImageFilename(String uploadImageFilename) {
//		this.uploadImageFilename = uploadImageFilename;
//	}

//	public AppUser getAppUser() {
//		return appUser;
//	}
//
//	public void setAppUser(AppUser appUser) {
//		this.appUser = appUser;
//	}

	
}

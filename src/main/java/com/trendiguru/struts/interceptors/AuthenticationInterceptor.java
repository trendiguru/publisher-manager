package com.trendiguru.struts.interceptors;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.trendiguru.entities.BaseUser;
import com.trendiguru.infra.Constants;
import com.trendiguru.struts.actions.SecureAction;
import com.trendiguru.struts.actions.UserAware;

/**
 * Intercept every HTTP request and check if there's a logged-in user.  If there is, then inject this user into the {@link SecureAction} so this and any sub-classes
 * can simply call getUser() rather than having to get it from the Session.
 * 
 * If there's no logged-in user then redirect to the log in page of the app.
 * 
 * Session Cookie no longer used since it's a HTTP Cookie ie set by the server "Set-Cookie".  When the app is removed from memory by iOS (when reclaiming resources) and the user enters the app
 * again, the app's state needs to be restored.  The HTTP cookie cannot be created in JS via document.cookie!  This is not sent to the server.  Calling a server API to get a new token is a possibility
 * but this would leave a stale session on the server.
 * 
 * Instead, I send the auth token in each request in the query-string.  This does not require an additional CORS pre-flight request per regular request!  Adding a "Authorization" HTTP heaeder does
 * cause an additional CORS pre-flight request so avoid it!  I tested it and it's true!  Also see https://github.com/dawithers/rootsmapper/issues/65
 * 
 * 
 * @author jeremy
 */
public class AuthenticationInterceptor extends AbstractInterceptor implements StrutsStatics {

	//private static final String FORBIDDEN_REPONSE = "accessForbidden";

	protected Logger log = LogManager.getLogger(this.getClass().getName());
	//private UserServices userServices;
		
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
    	
    	ActionContext context = invocation.getInvocationContext();
    	
    	Map<String, Object> session = context.getSession();
    	BaseUser user = (BaseUser)session.get(Constants.LOGGED_IN_USER);
    	
	    	
    	if (user == null) {
    		log.warn("Auth Int: There is no logged in user for the given session ID in memory.  Perhaps the server was re-started since the user last used us.");

    		return Action.LOGIN;
    	} else {
    		//log.info("user in session is: " + user.getEmail());
    		Action action = ( Action ) invocation.getAction();
            if (action instanceof UserAware) {                               
                ((UserAware)action).setUser(user);                         
            }
            log.warn("Auth Int: Injected logged-in user into secure action with email: " + user.getEmail());
            return invocation.invoke();
        }
	}
    
    
/*
	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}
*/
}
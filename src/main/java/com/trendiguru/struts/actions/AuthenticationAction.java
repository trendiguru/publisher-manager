package com.trendiguru.struts.actions;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.trendiguru.entities.Admin;
import com.trendiguru.entities.BaseUser;
import com.trendiguru.entities.Publisher;
import com.trendiguru.infra.Constants;
import com.trendiguru.infra.SessionCache;




/**
 * Provides logout, login
 * 
 * @author jeremyc
 *
 */
//@Validation
public class AuthenticationAction extends BaseAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AuthenticationAction.class); 
	//private static final String IMAGE_PATH = "C:/apps/scoochup/pics/";
	
	/** The Constant LOGGED_OUT. */
	public static final String LOGGED_OUT = "loggedOut";
	public static final String SESSION_TIME_OUT = "sessionTimeout";
	
	//private String email;
	//private String password;
	//private String newPassword;
	//private String confirmedPassword;
	private String jcaptcha;
	private String token;

	
	private BaseUser user;
	private Publisher publisher;
	private Admin admin;

	public String logOut() {
		SessionCache.getInstance().removeUser(token);
		
		//HttpSession session = request.getSession();
		//session.invalidate();
		return EMPTY;
	}
	

	
	/**
	 * Call this when logging in via email. Has Struts validation on email/passwd
	 * 
	 * @return
	 */
	public String login() {
		
		BaseUser loggedInUser = authenticationServices.login(user.getEmail(), user.getPassword());
		if (loggedInUser == null) {
			addActionError("user " + user.getEmail() + " does not exist");
			//addActionError(getText("auth.errors.user.does.not.exist"));
			return INPUT;
		} else {
			
			SessionCache.getInstance().addUser(loggedInUser.getEmail() + loggedInUser.getPassword(), loggedInUser);
			
			//TODO - use BaseAction.toJson() to convert BaseUser to json and put string in loggedin.jsp
			//
			//
			//
			
			
			token = getSession().getId();
			session.put(Constants.LOGGED_IN_USER, loggedInUser);
			
			if (loggedInUser instanceof Publisher) {
				publisher = (Publisher)loggedInUser;
				return LOGGED_IN;
			} else {
				admin = (Admin)loggedInUser;
				return LOGGED_IN;
			}
			
		}
	}
	/*
	public String forgotPassword() {
		authenticationServices.forgotPassword(appUser.getEmail());
		//addActionMessage(getText("auth.reset.password.message"));
		return EMPTY;
	}
	*/
	public String getJcaptcha() {
		return jcaptcha;
	}

	public void setJcaptcha(String jcaptcha) {
		this.jcaptcha = jcaptcha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String activationToken) {
		this.token = activationToken;
	}

	public BaseUser getUser() {
		return user;
	}

	public void setUser(BaseUser user) {
		this.user = user;
	}



	public Publisher getPublisher() {
		return publisher;
	}



	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}



	public Admin getAdmin() {
		return admin;
	}



	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

 
}

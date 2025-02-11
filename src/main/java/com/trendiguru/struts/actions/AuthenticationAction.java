package com.trendiguru.struts.actions;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mongodb.DuplicateKeyException;
import com.trendiguru.elasticsearch.PublisherManager;
import com.trendiguru.entities.BaseUser;
import com.trendiguru.entities.RoleEnum;
import com.trendiguru.entities.StatusEnum;
import com.trendiguru.entities.User;
import com.trendiguru.entities.visuals.AverageTimeOnSite;
import com.trendiguru.entities.visuals.ClickThruRateOnItemVisual;
import com.trendiguru.entities.visuals.ClickThruRateOnOurIconVisual;
import com.trendiguru.entities.visuals.DevicesVisual;
import com.trendiguru.entities.visuals.EventsMultiLineHistogramVisual;
import com.trendiguru.entities.visuals.EventsTableVisual;
import com.trendiguru.entities.visuals.RevenueVisual;
import com.trendiguru.entities.visuals.TrendingCategories;
import com.trendiguru.entities.visuals.TrendingImagesTableVisual;
import com.trendiguru.entities.visuals.UniqueUsers;
import com.trendiguru.entities.visuals.Visual;
import com.trendiguru.entities.visuals.WorldMapVisual;
import com.trendiguru.infra.Constants;
import com.trendiguru.infra.PasswordManager;
import com.trendiguru.infra.SessionCache;
import com.trendiguru.mongodb.MorphiaManager;




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
	private String passwordResetToken;

	
	private BaseUser user;
	private User publisher;
	//private Admin admin;

	public String signUp() {
	
		/* non null validation done on client */
		//Publisher publisher = new Publisher(publisher.getName(), publisher.getDomain(), publisher.getEmail(), publisher.getPassword());
    	
		String randomId = PasswordManager.getRandomPassword(16);
		publisher.setPid(randomId);
		publisher.setRole(RoleEnum.Publisher);
		publisher.setCreateDate(new Date());
		
    	PublisherManager publisherManager = PublisherManager.getInstance();
    	Set<Visual> visualSet = new HashSet<Visual>();
    	visualSet.add(new EventsTableVisual(publisher));
    	visualSet.add(new EventsMultiLineHistogramVisual(publisher));
    	visualSet.add(new DevicesVisual(publisher));
    	visualSet.add(new WorldMapVisual(publisher));
    	visualSet.add(new ClickThruRateOnOurIconVisual(publisher));
    	visualSet.add(new ClickThruRateOnItemVisual(publisher));
    	visualSet.add(new TrendingImagesTableVisual(publisher));
    	visualSet.add(new UniqueUsers(publisher));
    	visualSet.add(new TrendingCategories(publisher));
    	visualSet.add(new AverageTimeOnSite(publisher));
    	visualSet.add(new RevenueVisual(publisher));
    	    	    	
    	try {
    		publisherManager.add(publisher,  visualSet);
    		return "pid";
    	} catch (DuplicateKeyException de) {
    		//addActionError("email: " + publisher.getEmail() + " already exists!");
    		if (de.getErrorMessage().contains("name")) {
    			addFieldError("publisher.name", "The company name '" + publisher.getName() + "' has already been registered!!");
    		} else {
    			addFieldError("publisher.email", "The email '" + publisher.getEmail() + "' has already been registered!");
    		}
    		
			//addActionError(getText("auth.errors.user.does.not.exist"));
			return INPUT;
    	} 
		
	}
	
	
	public String signOut() {
		SessionCache.getInstance().removeUser(token);
		return EMPTY;
	}
	
	/**
	 * Call this when logging in via email. Has Struts validation on email/passwd
	 * 
	 * @return
	 */
	public String login() {
		
		User loggedInUser = authenticationServices.login(user.getEmail(), user.getPassword());
		if (loggedInUser == null) {
			//don't say email or password doesn't exist!
			addActionError("Problem logging in as '" + user.getEmail() + "' !");
			//addActionError(getText("auth.errors.user.does.not.exist"));
			return INPUT;
		} else {
			if (loggedInUser.getStatus().equals(StatusEnum.Active)) {
				SessionCache.getInstance().addUser(loggedInUser.getEmail() + loggedInUser.getPassword(), loggedInUser);
				
				//TODO - use BaseAction.toJson() to convert BaseUser to json and put string in loggedin.jsp
				//
				//
				//
				
				
				token = getSession().getId();
				session.put(Constants.LOGGED_IN_USER, loggedInUser);
				
				//if (loggedInUser instanceof User) {
					publisher = loggedInUser;
					return LOGGED_IN;
				//} else {
				//	admin = (Admin)loggedInUser;
				//	return LOGGED_IN;
				//}
			} else {
				addActionError("Your account is not active! Contact TrendiGuru!");
				return INPUT;
			}
			
		}
	}
	
	public String forgotPassword() {
		authenticationServices.forgotPassword(user.getEmail());
		//addActionMessage(getText("auth.reset.password.message"));
		return EMPTY;
	}
	
	public String resetPasswordForm() {
		publisher = MorphiaManager.getInstance().findUserToResetPassword(passwordResetToken);
		if (publisher != null && publisher.getStatus().equals(StatusEnum.ForgottenPassword)) {
			return "resetPasswordForm";
		} else {
			addActionError("'No such reset token exists!");
			return INPUT;
		}		
	}
	
	public String resetPassword() {
		authenticationServices.resetPassword(publisher);
		//addActionMessage(getText("auth.reset.password.message"));
		return EMPTY;
	}
	
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



	public User getPublisher() {
		return publisher;
	}



	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}


	public String getPasswordResetToken() {
		return passwordResetToken;
	}


	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}


/*
	public Admin getAdmin() {
		return admin;
	}



	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

 */
}

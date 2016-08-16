package com.trendiguru.services;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.trendiguru.elasticsearch.DashboardManager;
import com.trendiguru.elasticsearch.VisualizationManager;
import com.trendiguru.entities.StatusEnum;
import com.trendiguru.entities.User;
import com.trendiguru.entities.visuals.Visual;
import com.trendiguru.infra.EmailManager;
import com.trendiguru.infra.PasswordManager;
import com.trendiguru.mongodb.MorphiaManager;

public class AuthenticationServices {
	private static Logger log = Logger.getLogger(AuthenticationServices.class); 
	private static AuthenticationServices INSTANCE = new AuthenticationServices();
	
	public static AuthenticationServices getInstance() {
		return INSTANCE;
	}
	
	public User login(String email, String password) {
		User foundUser = MorphiaManager.getInstance().findUser(email);
		
		if (foundUser == null) {
			return null;
		} else {
			byte[] incomingHashedSaltedPasswordAsBytes;
			try {
				incomingHashedSaltedPasswordAsBytes = PasswordManager.getHashWithSalt(password, "SHA-256", PasswordManager.stringToByte(foundUser.getSalt()));
				String incomingHashedSaltedPasswordAsString = PasswordManager.bytetoString(incomingHashedSaltedPasswordAsBytes);
				
				if (incomingHashedSaltedPasswordAsString.equals(foundUser.getPassword())) {
						foundUser.setLastLoginDate(new Date());
						MorphiaManager.getInstance().updatePublisher(foundUser);
						
						log.info("Found authenticated user: " + foundUser.getEmail());
						return foundUser;
				} else {
					return null;
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	public void forgotPassword(String email) {
		User foundUser = MorphiaManager.getInstance().findUser(email);
		
		if (foundUser == null) {
			log.warn("Forgotten password requested by email: " + email + " not in our DB!");
			//return null;
		} else {
			foundUser.setStatus(StatusEnum.ForgottenPassword);
			String resetToken = PasswordManager.getRandomPassword(20);
			foundUser.setPasswordResetToken(resetToken);
			MorphiaManager.getInstance().updatePublisher(foundUser);
			EmailManager.forgottenPassword(foundUser);
			//create hash
			//save hash on user
			//email hash to user
			
		}

		//return foundUser;
	}
	
	public void resetPassword(User resetUser) {
		User foundUser = MorphiaManager.getInstance().findUserToResetPassword(resetUser.getPasswordResetToken());
		
		if (foundUser == null) {
			log.warn("Password reset requested by email: " + foundUser.getEmail() + " not in our DB!");
			//return null;
		} else {
			
			if (foundUser.getPasswordResetToken().equals(resetUser.getPasswordResetToken())) {
				
				byte[] salt = PasswordManager.generateSalt();
				
				try {
					byte[] hashedSaltedPasswordAsBytes = PasswordManager.getHashWithSalt(resetUser.getPassword(), "SHA-256", salt);
					String hashedSaltedPasswordAsString = PasswordManager.bytetoString(hashedSaltedPasswordAsBytes);
					
					foundUser.setSalt(PasswordManager.bytetoString(salt));
					foundUser.setPassword(hashedSaltedPasswordAsString);
							
					foundUser.setStatus(StatusEnum.Active);
					foundUser.setPasswordResetToken(null);
					MorphiaManager.getInstance().updatePublisher(foundUser);
					EmailManager.passwordReset(foundUser, resetUser.getPassword());

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.fatal(e);
				}
			
				
			}
			//create hash
			//save hash on user
			//email hash to user
			
		}
	}
}

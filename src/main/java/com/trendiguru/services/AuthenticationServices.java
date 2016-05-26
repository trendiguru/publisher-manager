package com.trendiguru.services;

import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.trendiguru.entities.BaseUser;
import com.trendiguru.entities.User;
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
					
						log.info("Found user: " + foundUser.getEmail());
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
}

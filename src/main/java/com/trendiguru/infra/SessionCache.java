package com.trendiguru.infra;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.trendiguru.entities.BaseUser;

public class SessionCache {
	private Logger log = LogManager.getLogger(this.getClass().getName());
	private Map<String, BaseUser> sessionCacheMap = new HashMap<String, BaseUser>();
	private static SessionCache INSTANCE = new SessionCache();
	
	private SessionCache() {
	}
	
	public static SessionCache getInstance() {
		return INSTANCE;
	}
	
	public void addUser(String sourceTextForToken, BaseUser appUser) {
		String token = generateToken(sourceTextForToken);
		appUser.setToken(token);
		this.sessionCacheMap.put(token, appUser);
		analyseCache();
	}
	
	public void updateUser(String token, BaseUser updatedUser) {
		this.sessionCacheMap.put(token, updatedUser);
		analyseCache();
	}
	
	public void removeUser(String token) {
		this.sessionCacheMap.remove(token);
		analyseCache();
	}
	
	public BaseUser getUser(String token) {
		return this.sessionCacheMap.get(token);
	}
	
	public void analyseCache() {
		//log.info("User session cache size: " + this.sessionCacheMap.size());
		String a = "";
		for (Map.Entry<String, BaseUser> entry : this.sessionCacheMap.entrySet())
		{
			a += entry.getValue().getEmail() + ", ";
		    //System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		log.info("User session cache size: " + this.sessionCacheMap.size() + ": " + a);
		
	}
	
	//TODO - use salt and hash here
	public String generateToken(String sourceTextForToken) {
		
		return PasswordManager.getRandomPassword(40);
	}
	
}

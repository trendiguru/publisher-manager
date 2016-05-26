package com.trendiguru.struts.actions;

import com.trendiguru.elasticsearch.PublisherManager;
import com.trendiguru.entities.User;

public class AdminAction extends SecureAction {
	private User publisher;
	
	public String addPublisher() {
    	//User publisher = new User("DigitalSpy","http://www.digitalspy.com/", "user@digitalspy.com", "mypassword");
    	
    	PublisherManager publisherManager = PublisherManager.getInstance();
    	
    	//publisherManager.add(publisher,  true, true);
    	
		return EMPTY;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}
	
	
}

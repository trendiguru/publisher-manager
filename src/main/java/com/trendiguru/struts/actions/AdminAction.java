package com.trendiguru.struts.actions;

import com.trendiguru.elasticsearch.PublisherManager;
import com.trendiguru.entities.Publisher;

public class AdminAction extends SecureAction {
	private Publisher publisher;
	
	public String addPublisher() {
    	Publisher publisher = new Publisher("DigitalSpy","http://www.digitalspy.com/", "user@digitalspy.com", "mypassword");
    	
    	PublisherManager publisherManager = PublisherManager.getInstance();
    	
    	//publisherManager.add(publisher,  true, true);
    	
		return EMPTY;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
	
}

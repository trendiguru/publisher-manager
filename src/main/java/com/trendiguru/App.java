package com.trendiguru;

import com.trendiguru.elasticsearch.PublisherManager;
import com.trendiguru.entities.Publisher;

//TODO - delete, replaced by AdminAction.addPublisher()

/**
 * Offline script that creates a new publisher:
 * 1. Add user to DB with login
 * 2. Creates dashboard
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Publisher publisher = new Publisher("DigitalSpy","http://www.digitalspy.com/", "user@digitalspy.com", "mypassword");
    	
    	PublisherManager publisherManager = PublisherManager.getInstance();
    	publisherManager.add(publisher,  true, true);
    }
}

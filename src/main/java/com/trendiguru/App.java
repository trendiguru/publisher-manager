package com.trendiguru;

import java.util.HashSet;
import java.util.Set;

import com.trendiguru.elasticsearch.PublisherManager;
import com.trendiguru.entities.Publisher;
import com.trendiguru.entities.visuals.ClickThruRateOnOurIconVisual;
import com.trendiguru.entities.visuals.DevicesVisual;
import com.trendiguru.entities.visuals.EventsVisual;
import com.trendiguru.entities.visuals.Visual;
import com.trendiguru.entities.visuals.WorldMapVisual;

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
    	Publisher publisher = new Publisher("Fashion Celeb Style","http://fashioncelebstyle.com/", "user@fcs.com", "mypassword");
    	
    	PublisherManager publisherManager = PublisherManager.getInstance();
    	Set<Visual> visualSet = new HashSet<Visual>();
    	visualSet.add(new EventsVisual(publisher));
    	visualSet.add(new DevicesVisual(publisher));
    	visualSet.add(new WorldMapVisual(publisher));
    	visualSet.add(new ClickThruRateOnOurIconVisual(publisher));
    	
    	
    	publisherManager.add(publisher,  visualSet);
    }
}

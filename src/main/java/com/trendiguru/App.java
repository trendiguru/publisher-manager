package com.trendiguru;

import java.util.HashSet;
import java.util.Set;

import com.trendiguru.elasticsearch.PublisherManager;
import com.trendiguru.entities.Publisher;
import com.trendiguru.entities.visuals.AverageTimeOnSite;
import com.trendiguru.entities.visuals.ClickThruRateOnItemVisual;
import com.trendiguru.entities.visuals.ClickThruRateOnOurIconVisual;
import com.trendiguru.entities.visuals.DevicesVisual;
import com.trendiguru.entities.visuals.EventsVisual;
import com.trendiguru.entities.visuals.TrendingCategories;
import com.trendiguru.entities.visuals.TrendingImagesVisual;
import com.trendiguru.entities.visuals.UniqueUsers;
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
    	//Publisher publisher = new Publisher("FashionSeoul","fashionseoul.com", "tracker@fashionseoul.com", "luoesnoihsaf");
    	//Publisher publisher = new Publisher("Rob Shelter","robshelter.blogspot.co.il", "robsdemartino@yahoo.it", "Robsdemartino465");
    	Publisher publisher = new Publisher("Jeremy Test", "fashioncelebstyle.com", "jscolton@gmail.com", "123456");
    	//Publisher publisher = new Publisher("Trendi Guru Admin", "", "support@trendiguru.com", "jacksnack");
    	
    	PublisherManager publisherManager = PublisherManager.getInstance();
    	Set<Visual> visualSet = new HashSet<Visual>();
    	visualSet.add(new EventsVisual(publisher));
    	visualSet.add(new DevicesVisual(publisher));
    	visualSet.add(new WorldMapVisual(publisher));
    	visualSet.add(new ClickThruRateOnOurIconVisual(publisher));
    	visualSet.add(new ClickThruRateOnItemVisual(publisher));
    	visualSet.add(new TrendingImagesVisual(publisher));
    	visualSet.add(new UniqueUsers(publisher));
    	visualSet.add(new TrendingCategories(publisher));
    	visualSet.add(new AverageTimeOnSite(publisher));
    	    	
    	publisherManager.add(publisher,  visualSet);
    }
}

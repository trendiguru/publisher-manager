package com.trendiguru;

import java.util.HashSet;
import java.util.Set;

import com.trendiguru.elasticsearch.PublisherManager;
import com.trendiguru.entities.RoleEnum;
import com.trendiguru.entities.User;
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
    	//Publisher user = new Publisher("FashionSeoul","fashionseoul.com", "tracker@fashionseoul.com", "luoesnoihsaf");
    	//Publisher user = new Publisher("Rob Shelter","robshelter.blogspot.co.il", "robsdemartino@yahoo.it", "Robsdemartino465");
    	//Publisher user = new Publisher("Jeremy Test", "fashioncelebstyle.com", "jscolton@gmail.com", "123456");
    	//Publisher user = new Publisher("Trendi Guru Admin", "", "support@trendiguru.com", "jacksnack");
    	
    	User user = new User("Trendi Guru Admin", "abc", "support@trendiguru2.com", "jacksnack", RoleEnum.Admin);
    	    	
    	PublisherManager publisherManager = PublisherManager.getInstance();
    	Set<Visual> visualSet = new HashSet<Visual>();
    	visualSet.add(new EventsVisual(user));
    	visualSet.add(new DevicesVisual(user));
    	visualSet.add(new WorldMapVisual(user));
    	visualSet.add(new ClickThruRateOnOurIconVisual(user));
    	visualSet.add(new ClickThruRateOnItemVisual(user));
    	visualSet.add(new TrendingImagesVisual(user));
    	visualSet.add(new UniqueUsers(user));
    	visualSet.add(new TrendingCategories(user));
    	visualSet.add(new AverageTimeOnSite(user));
    	    	
    	//publisherManager.add(publisher,  visualSet);
    	publisherManager.add(user,  visualSet);
    }
}

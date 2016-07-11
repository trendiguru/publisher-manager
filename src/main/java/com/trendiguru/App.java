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
import com.trendiguru.entities.visuals.EventsTableVisual;
import com.trendiguru.entities.visuals.RevenueVisual;
import com.trendiguru.entities.visuals.TrendingCategories;
import com.trendiguru.entities.visuals.TrendingImagesTableVisual;
import com.trendiguru.entities.visuals.UniqueUsers;
import com.trendiguru.entities.visuals.Visual;
import com.trendiguru.entities.visuals.WorldMapVisual;
import com.trendiguru.infra.PasswordManager;

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
    	//User user = new User("FashionSeoul","fashionseoul.com", "tracker@fashionseoul.com", "luoesnoihsaf", RoleEnum.Publisher);
    	//User user = new User("Rob Shelter","robshelter.blogspot.co.il", "robsdemartino@yahoo.it", "Robsdemartino465", RoleEnum.Publisher);
    	//User user = new User("Venus Imaging Education LLC","amaze-magazine.com", "connect@viethrive.com", "Robsdemartino465", RoleEnum.Publisher);
    	//User user = new User("couponroller","Shaun", "shaun@couponroller.com", "Robsdemartino465", RoleEnum.Publisher);
    	
    	//User user = new User("Jeremy Test", "fashioncelebstyle.com", "jscolton@gmail.com", "123456", RoleEnum.Publisher);
    	//User user = new User("Trendi Guru Admin", "", "support@trendiguru.com", "jacksnack");
    	
    	//Admin
    	User user = new User("Trendi Guru Admin", "abc", "support@trendiguru.com", "jacksnack", RoleEnum.Admin);
    	    	
		String randomId = PasswordManager.getRandomPassword(16);
		//user.setPid(randomId);
		user.setPid("123");
    	
    	PublisherManager publisherManager = PublisherManager.getInstance();
    	Set<Visual> visualSet = new HashSet<Visual>();
    	visualSet.add(new EventsTableVisual(user));
    	visualSet.add(new DevicesVisual(user));
    	visualSet.add(new WorldMapVisual(user));
    	visualSet.add(new ClickThruRateOnOurIconVisual(user));
    	visualSet.add(new ClickThruRateOnItemVisual(user));
    	visualSet.add(new TrendingImagesTableVisual(user));
    	visualSet.add(new UniqueUsers(user));
    	visualSet.add(new TrendingCategories(user));
    	visualSet.add(new AverageTimeOnSite(user));
    	visualSet.add(new RevenueVisual(user));
    	    	
    	//publisherManager.add(publisher,  visualSet);
    	publisherManager.add(user,  visualSet);
    }
}

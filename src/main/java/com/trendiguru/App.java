package com.trendiguru;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.trendiguru.elasticsearch.PublisherManager;
import com.trendiguru.entities.RoleEnum;
import com.trendiguru.entities.User;
import com.trendiguru.entities.visuals.AverageTimeOnSite;
import com.trendiguru.entities.visuals.ClickThruRateOnItemVisual;
import com.trendiguru.entities.visuals.ClickThruRateOnOurIconVisual;
import com.trendiguru.entities.visuals.DevicesVisual;
import com.trendiguru.entities.visuals.EventsMultiLineHistogramVisual;
import com.trendiguru.entities.visuals.EventsTableVisual;
import com.trendiguru.entities.visuals.RevenueVisual;
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
    	
    	Map<String, User> userMap = new HashMap<String, User>();
    	userMap.put("fashionseoul", new User("FashionSeoul","Support", "support@fashionseoul.com", "luoesnoihsaf", RoleEnum.Publisher));
    	userMap.put("robsdemartino@yahoo.it", new User("Rob Shelter","Rob", "robsdemartino@yahoo.it", "Robsdemartino465", RoleEnum.Publisher));
    	userMap.put("xuSiNIs695acaHPE", new User("Venus Imaging Education LLC","connect", "connect@viethrive.com", "Robsdemartino465", RoleEnum.Publisher));
    	userMap.put("R8t1x8iJ3BB6UM69", new User("couponroller","Shaun", "shaun@couponroller.com", "Robsdemartino465", RoleEnum.Publisher));
    	userMap.put("wKFr87LG47Flqq45", new User("sharonel","Sharon", "sharonel22@yahoo.com", "XXX", RoleEnum.Publisher));
    	userMap.put("6t50LSJxeNEkQ42p", new User("recruit","Shirai Yusuke", "yusuke_shirai@r.recruit.co.jp", "XXX", RoleEnum.Publisher));
    	//userMap.put("5767jA8THOn2J0DD", new User("fashionseoul","ethankim", "ethankim@fashionseoul.com", "XXX", RoleEnum.Publisher));
    	userMap.put("FCZEUP7IjVXV2s23", new User("Jeremy Test", "fashioncelebstyle", "jscolton@gmail.com", "123456", RoleEnum.Publisher));
    	userMap.put("*", new User("Trendi Guru Admin", "Admin", "support@trendiguru.com", "jacksnack", RoleEnum.Admin));
    	
    	//chrome plugin distributor
    	userMap.put("mz1_ND", new User("Monetizus","Monetizus", "reborn@monetizus.com", "changetheworld", RoleEnum.Publisher));

    	userMap.put("iko5kmX5jkVy2053", new User("TG Website","Kyle", "test1@trendiguru.com", "xxx", RoleEnum.Publisher));
    	
    	
    	//User user = new User("FashionSeoul","Support", "support@fashionseoul.com", "luoesnoihsaf", RoleEnum.Publisher);
    	//User user = new User("Rob Shelter","Rob", "robsdemartino@yahoo.it", "Robsdemartino465", RoleEnum.Publisher);
    	//User user = new User("Venus Imaging Education LLC","connect", "connect@viethrive.com", "Robsdemartino465", RoleEnum.Publisher);
    	//User user = new User("couponroller","Shaun", "shaun@couponroller.com", "Robsdemartino465", RoleEnum.Publisher);
    	//User user = new User("sharonel","Sharon", "sharonel22@yahoo.com", "XXX", RoleEnum.Publisher);
    	//User user = new User("recruit","Shirai Yusuke", "yusuke_shirai@r.recruit.co.jp", "XXX", RoleEnum.Publisher);
    	//User user = new User("fashionseoul","ethankim", "ethankim@fashionseoul.com", "XXX", RoleEnum.Publisher);
    	
    	//User user = new User("Jeremy Test", "fashioncelebstyle", "jscolton@gmail.com", "123456", RoleEnum.Publisher);
    	//User user = new User("Trendi Guru Admin", "", "support@trendiguru.com", "jacksnack");
    	
    	//Admin
    	//User user = new User("Trendi Guru Admin", "abc", "support@trendiguru.com", "jacksnack", RoleEnum.Admin);
    	    	
    	
    	for (Map.Entry<String, User> entry : userMap.entrySet()) {
    		System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
    		
    		User user = entry.getValue();
    	
			String randomId = PasswordManager.getRandomPassword(16);
			//user.setPid(randomId);
			user.setPid(entry.getKey());
	    	
	    	PublisherManager publisherManager = PublisherManager.getInstance();
	    	Set<Visual> visualSet = new HashSet<Visual>();
	    	visualSet.add(new EventsTableVisual(user));
	    	visualSet.add(new EventsMultiLineHistogramVisual(user));
	    	visualSet.add(new DevicesVisual(user));
	    	visualSet.add(new WorldMapVisual(user));
	    	visualSet.add(new ClickThruRateOnOurIconVisual(user));
	    	visualSet.add(new ClickThruRateOnItemVisual(user));
	    	visualSet.add(new TrendingImagesTableVisual(user));
	    	visualSet.add(new UniqueUsers(user));
	    	//visualSet.add(new TrendingCategories(user));
	    	visualSet.add(new AverageTimeOnSite(user));
	    	visualSet.add(new RevenueVisual(user));
	    	    	
	    	//publisherManager.add(publisher,  visualSet);
	    	publisherManager.add(user,  visualSet);
    	}
    }
}

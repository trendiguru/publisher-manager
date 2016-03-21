package com.trendiguru.elasticsearch;

import com.trendiguru.entities.Publisher;
import com.trendiguru.mongodb.MongoManager;
import com.trendiguru.mongodb.MorphiaManager;

public class PublisherManager {

	public PublisherManager() {
	}
	
	public void add(Publisher publisher, boolean addDataTable, boolean addHistogram) {
		MorphiaManager morphiaManager = MorphiaManager.getInstance();
		morphiaManager.addPublisher(publisher);
		
		/*
    	MongoManager mongoManager = MongoManager.getInstance();
    	mongoManager.addPublisher(publisher);
    	*/
		
    	/*
    	//TODO - check booleans to decide which visuals to add
    	VisualizationManager manager = new VisualizationManager();
    	manager.addDataTable(publisher);
    	manager.addHistoGram(publisher);
    	
    	DashboardManager dbManager = new DashboardManager();
    	
    	//TODO - add visuals here too
    	dbManager.addDashBoard(publisher, null);	
    	*/
	}
}

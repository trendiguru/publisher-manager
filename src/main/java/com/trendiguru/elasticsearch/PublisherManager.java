package com.trendiguru.elasticsearch;

import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.trendiguru.entities.Publisher;
import com.trendiguru.infra.PasswordManager;
import com.trendiguru.mongodb.MorphiaManager;

public class PublisherManager {
	private static Logger log = Logger.getLogger(PublisherManager.class);
	public static PublisherManager INSTANCE = new PublisherManager();
	
	private PublisherManager() {
	}
	
	public static PublisherManager getInstance() {
		return INSTANCE;
	}
	
	public void add(Publisher publisher, boolean addDataTable, boolean addHistogram) {
		MorphiaManager morphiaManager = MorphiaManager.getInstance();
		
		byte[] salt = PasswordManager.generateSalt();
		
		try {
			byte[] hashedSaltedPasswordAsBytes = PasswordManager.getHashWithSalt(publisher.getPassword(), "SHA-256", salt);
			String hashedSaltedPasswordAsString = PasswordManager.bytetoString(hashedSaltedPasswordAsBytes);
			
			publisher.setSalt(PasswordManager.bytetoString(salt));
			publisher.setPassword(hashedSaltedPasswordAsString);
			
			morphiaManager.addPublisher(publisher);
			log.info("added to mongodb publisher: " + publisher.getEmail());

			
			/*
	    	MongoManager mongoManager = MongoManager.getInstance();
	    	mongoManager.addPublisher(publisher);
	    	*/
			
	    	
	    	//TODO - check booleans to decide which visuals to add
	    	VisualizationManager manager = new VisualizationManager();
	    	manager.addDataTable(publisher);
	    	manager.addHistoGram(publisher);
	    	
	    	DashboardManager dbManager = new DashboardManager();
	    	
	    	//TODO - add visuals here too
	    	dbManager.addDashBoard(publisher, null);
	    	log.info("added kibana dashboard for publisher: " + publisher.getEmail());
	    	
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//TODO - delete?
	@Deprecated
	public String getKibanaDashboard(Publisher publisher) {
		//http://localhost:9000/goto/09dd62a2c2e9fd7f27600d8a29603663
		
		//TODO - integrate with various APIs...
		
		
		
		DashboardManager dashboardManager = new DashboardManager();
		String kibanaDashboard = dashboardManager.read("app/kibana#/dashboard/Jeremy's-dash?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fw,mode:quick,to:now%2Fw))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:1,id:Client-IPs,panelIndex:1,row:1,size_x:3,size_y:2,type:visualization),(col:4,id:Events-breakdown-per-week,panelIndex:2,row:1,size_x:3,size_y:2,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:'Jeremy!'s%20dash',uiState:())", "GET", null);
		return kibanaDashboard;
	}
	
}

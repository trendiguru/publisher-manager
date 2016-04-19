package com.trendiguru.elasticsearch;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.trendiguru.entities.Publisher;
import com.trendiguru.infra.PasswordManager;
import com.trendiguru.mongodb.MorphiaManager;
import com.trendiguru.entities.visuals.Visual;

public class PublisherManager {
	private static Logger log = Logger.getLogger(PublisherManager.class);
	public static PublisherManager INSTANCE = new PublisherManager();
	
	private PublisherManager() {
	}
	
	public static PublisherManager getInstance() {
		return INSTANCE;
	}
	
	public void add(Publisher publisher, Set<Visual> visualsToAddSet) {
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
			
	    	VisualizationManager manager = new VisualizationManager();
	    	for (Visual visual : visualsToAddSet) {
	    		//String encodedEntityName = publisher.getEncodedName() + "-" + visual.getElasticSearchIdSuffix();
	    		manager.add(publisher, visual.getEncodedJSON(), visual.getElasticSearchId());
	    	}
	    	
	    	DashboardManager dbManager = new DashboardManager(publisher);
	    	
	    	//TODO - add visuals here too
	    	dbManager.addDashBoard(visualsToAddSet);
	    	log.info("added kibana dashboard for publisher: " + publisher.getEmail());
	    	
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

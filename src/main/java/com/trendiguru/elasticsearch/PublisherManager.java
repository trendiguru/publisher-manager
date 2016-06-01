package com.trendiguru.elasticsearch;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mongodb.DuplicateKeyException;
import com.trendiguru.entities.User;
import com.trendiguru.entities.visuals.Visual;
import com.trendiguru.infra.EmailManager;
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
	
	public void add(User publisher, Set<Visual> visualsToAddSet) throws DuplicateKeyException {
		//try {
			MorphiaManager morphiaManager = MorphiaManager.getInstance();
			
			byte[] salt = PasswordManager.generateSalt();
			
			try {
				byte[] hashedSaltedPasswordAsBytes = PasswordManager.getHashWithSalt(publisher.getPassword(), "SHA-256", salt);
				String hashedSaltedPasswordAsString = PasswordManager.bytetoString(hashedSaltedPasswordAsBytes);
				
				publisher.setSalt(PasswordManager.bytetoString(salt));
				publisher.setPassword(hashedSaltedPasswordAsString);
						
				//1. add user to Mongo
				morphiaManager.addPublisher(publisher);
				log.info("1. Added to mongodb, publisher: " + publisher.getEmail());
				
		    	VisualizationManager manager = new VisualizationManager();
		    	
		    	for (Visual visual : visualsToAddSet) {
		    		//String encodedEntityName = publisher.getEncodedName() + "-" + visual.getElasticSearchIdSuffix();
		    		
		    		//2. add Kibana visuals
		    		manager.add(publisher, visual.getEncodedJSON(), visual.getElasticSearchId());
		    	}
		    	log.info("2. Added visuals for publisher: " + publisher.getName() + " and domain: " + publisher.getDomain());
		    	
		    	DashboardManager dbManager = new DashboardManager(publisher);
		    	
		    	//3. add kibana dashboard that references all the added visuals above
		    	dbManager.addDashBoard(visualsToAddSet);
		    	log.info("3. Added dashboard for publisher: " + publisher.getName() + " and domain: " + publisher.getDomain());
		    	
		    	//4. email biz people
		    	EmailManager.newSignUpNotifyTrendiGuru(publisher);
		    	
		    	//5. email publisher of their login info
		    	EmailManager.newSignUpNotifyPublisher(publisher);
		    			    	
		    	//log.info("added kibana dashboard for publisher: " + publisher.getEmail());
		    	
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.fatal(e);
			}
		//} catch (Throwable t) {
		//	log.fatal(t.getStackTrace(), t);
		//	t.printStackTrace();
		//	if (t instanceof DuplicateKeyException) {
		//		throw DuplicateKeyException;
		//	}
		//}
		
	}
	
}

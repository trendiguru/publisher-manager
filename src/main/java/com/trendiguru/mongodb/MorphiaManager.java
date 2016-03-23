package com.trendiguru.mongodb;

import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.trendiguru.entities.BaseUser;
import com.trendiguru.entities.Publisher;

/**
 * {@link http://mongodb.github.io/morphia/1.1/getting-started/quick-tour/}
 * 
 * @author Jeremy
 *
 */
public class MorphiaManager {
	private static Logger log = Logger.getLogger(MorphiaManager.class);
	private static MorphiaManager INSTANCE = new MorphiaManager();
	final Morphia morphia = new Morphia();
	final Datastore datastore;
	
	
	private MorphiaManager() {
		morphia.mapPackage("com.trendiguru.entities");
		datastore = morphia.createDatastore(new MongoClient("localhost"), "mydb");
		datastore.ensureIndexes();
	}
	
	public static MorphiaManager getInstance() {
		return INSTANCE;
	}
	
	public void addPublisher(Publisher publisher) {
		datastore.save(publisher);
	}
	
	public BaseUser findBaseUser(String email) {
		try {
			List<BaseUser> publisherList = datastore.createQuery(BaseUser.class)
	                .field("email").equal(email)
	                .asList();
			
			if (publisherList.size() == 1) {
				return publisherList.get(0);
			} else if (publisherList.isEmpty()) {
				return null;
			} else {
				return null;
			}
		} catch (Throwable t) {
			log.fatal(t);
			return null;
		}
	}
}

package com.trendiguru.mongodb;

import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import com.trendiguru.config.ConfigManager;
import com.trendiguru.entities.BaseUser;
import com.trendiguru.entities.RoleEnum;
import com.trendiguru.entities.User;

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
	private ConfigManager configManager;
	
	
	private MorphiaManager() {
		configManager = ConfigManager.getInstance();
		morphia.mapPackage("com.trendiguru.entities");
		//datastore = morphia.createDatastore(new MongoClient("localhost"), "mydb");
		datastore = morphia.createDatastore(new MongoClient(configManager.getMongoDomain()), "mydb");
		
		datastore.ensureIndexes();
	}
	
	public static MorphiaManager getInstance() {
		return INSTANCE;
	}
	
	public void addPublisher(User publisher) throws DuplicateKeyException {
		//try {
		datastore.ensureIndexes();
		datastore.save(publisher);
		//} catch (DuplicateKeyException de) {
			
		//}
	}
	
	/**
	 * {@link http://mongodb.github.io/morphia/1.2/getting-started/quick-tour/}
	 * 
	 * @param publisher
	 * @throws DuplicateKeyException
	 */
	public void updatePublisher(User publisher) throws DuplicateKeyException {
		/* just call .save() with the updated object */
		addPublisher(publisher);
	}
	
	public User findUser(String email) {
		try {
			List<User> publisherList = datastore.createQuery(User.class)
	                .field("email").equal(email)
	                .asList();
			
			if (publisherList.size() == 1) {
				return publisherList.get(0);
			} else if (publisherList.isEmpty()) {
				return null;
			} else {
				log.fatal("There is > 1 user with the email: " + email + " in the DB!");
				return null;
			}
		} catch (Throwable t) {
			log.fatal(t);
			return null;
		}
	}
	
	public User findUserToResetPassword(String resetToken) {
		try {
			List<User> publisherList = datastore.createQuery(User.class)
	                .field("passwordResetToken").equal(resetToken)
	                .asList();
			
			if (publisherList.size() == 1) {
				return publisherList.get(0);
			} else if (publisherList.isEmpty()) {
				return null;
			} else {
				log.fatal("There is > 1 user with the passwordResetToken: " + resetToken + " in the DB!");
				return null;
			}
		} catch (Throwable t) {
			log.fatal(t);
			return null;
		}
	}
	
	public List<User> findAll() {
		try {
			List<User> publisherList = datastore.createQuery(User.class).field("role").notEqual(RoleEnum.Admin).asList();
			return publisherList;
		} catch (Throwable t) {
			log.fatal(t);
			return null;
		}
	}
}

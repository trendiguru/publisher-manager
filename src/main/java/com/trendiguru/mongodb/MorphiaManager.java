package com.trendiguru.mongodb;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.trendiguru.entities.Publisher;

/**
 * {@link http://mongodb.github.io/morphia/1.1/getting-started/quick-tour/}
 * 
 * @author Jeremy
 *
 */
public class MorphiaManager {
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
}

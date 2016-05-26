package com.trendiguru.mongodb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.trendiguru.entities.User;

public class MongoManager {
	private static MongoManager INSTANCE = new MongoManager();
	MongoDatabase db = null;
	
	private MongoManager() {
		MongoClient mongoClient = new MongoClient("146.148.10.50");
		db = mongoClient.getDatabase("mydb");
		
		//username/password?
	}
	
	public static MongoManager getInstance() {
		return INSTANCE;
	}

	public void addPublisher(User publisher) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		
		//TODO - store entity - how? i don't want to convert it to a doc!
		MongoCollection collection = db.getCollection("publishers");
		/*
		Document newPublisher = new Document("address",
                new Document()
        .append("street", "2 Avenue")
        .append("zipcode", "10075")
        .append("building", "1480")
        .append("coord", asList(-73.9557413, 40.7720266)))
.append("borough", "Manhattan")
.append("cuisine", "Italian")
.append("grades", asList(
        new Document()
                .append("date", format.parse("2014-10-01T00:00:00Z"))
                .append("grade", "A")
                .append("score", 11),
        new Document()
                .append("date", format.parse("2014-01-16T00:00:00Z"))
                .append("grade", "B")
                .append("score", 17)))
.append("name", "Vella")
.append("restaurant_id", "41704620"));
		
		collection.insertOne(newPublisher);
		*/
		
		                
	}
}

package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;

public class TimeOnSiteVisual extends Visual {

	public TimeOnSiteVisual(Publisher publisher) {
		this.publisher = publisher;
		this.elasticSearchId = publisher.getEncodedName() + "-world-map";
		this.title = publisher.getName() + " World Map";
	}
	
	@Override
	public String getEncodedJSON() {
		// TODO Auto-generated method stub
		return null;
	}
}

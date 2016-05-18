package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;

public class TimeOnSiteVisual extends Visual {

	public TimeOnSiteVisual(Publisher publisher) {
		this.publisher = publisher;
		//this.elasticSearchId = publisher.getEncodedName() + "-world-map";
		this.title = publisher.getName() + " World Map";
		
		//this is what Kibana does when adding a new visual so I'll mirror this so I can add new visuals to existing dashboards
		this.elasticSearchId = this.title.replace(" ", "-");
	}
	
	@Override
	public String getEncodedJSON() {
		// TODO Auto-generated method stub
		return null;
	}
}

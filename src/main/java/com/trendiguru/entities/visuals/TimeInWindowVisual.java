package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;

public class TimeInWindowVisual extends Visual {

	public TimeInWindowVisual(Publisher publisher) {
		this.elasticSearchId = publisher.getEncodedName() + "-world-map";
		this.title = publisher.getName() + " World Map";
	}
	
	@Override
	public String getEncodedJSON(Publisher publisher) {
		// TODO Auto-generated method stub
		return null;
	}
}

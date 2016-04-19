package com.trendiguru.entities.visuals;

import com.trendiguru.entities.Publisher;


public abstract class Visual {
	//VisualEnum type;
	String elasticSearchId;	// the ES id is made of 2 parts: prefix and suffix eg "fashion-celeb-style" + "world-map"
	String title;
	String encodedJSON;
	Publisher publisher;
	
	/**
	 * {@link http://www.infobyip.com/jsonencoderdecoder.php} to encode the JSON
	 * 
	 * @param publisher
	 * @return
	 */
	public abstract String getEncodedJSON();

	public String getElasticSearchId() {
		return elasticSearchId;
	}

	public void setElasticSearchId(String elasticSearchId) {
		this.elasticSearchId = elasticSearchId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getKibanaIncludePatternValue() {
		return ".*" + publisher.getDomain() + ".*";
	}
	
	
}

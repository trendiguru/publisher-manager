package com.trendiguru.elasticsearch;


public class VisualizationManager extends KibanaManager {
	
	public VisualizationManager() {
	}
	
	public String getESEntityUrl(String visualId) {
		return "http://localhost:9000/elasticsearch/.kibana/visualization/" + visualId + "?op_type=create";
	}
	
}

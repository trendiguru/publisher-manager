package com.trendiguru;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.trendiguru.entities.dashboard.DashboardFilterData;

//TODO - delete, replaced by AdminAction.addPublisher()

/**
 * 
 */
public class Tester 
{

	private static Logger log = Logger.getLogger(DashboardFilterData.class);
	
    public static void main( String[] args )
    {	    	
    	DashboardFilterData filterManager = DashboardFilterData.getInstance();
	    Set<String> allowedFieldsSet = filterManager.getAllFilterFieldNamesForOurIndex();
	    
	    int NUM_OF_VALUES = 400;
	    
	    for (String field : allowedFieldsSet) {
	    	String json = filterManager.getFilterFieldValuesPerPIDJSON("fashionseoul", field, NUM_OF_VALUES);
	    	List<String> fieldValues = filterManager.getFieldValues(json);
	    	log.info(field + " : " + fieldValues);
	    }
    }
}

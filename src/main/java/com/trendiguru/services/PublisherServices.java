package com.trendiguru.services;

public class PublisherServices {
	private static PublisherServices INSTANCE = new PublisherServices();
	
	public static PublisherServices getInstance() {
		return INSTANCE;
	}
}

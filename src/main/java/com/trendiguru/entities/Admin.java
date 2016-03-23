package com.trendiguru.entities;

import org.mongodb.morphia.annotations.Entity;

@Entity("users")
public class Admin extends BaseUser {

	public Admin() {
	}
}

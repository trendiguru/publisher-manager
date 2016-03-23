package com.trendiguru.entities;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("users")
@Indexes(
    @Index(value = "domain", fields = @Field("domain"))
)
public class Publisher extends BaseUser {
	String name;
	String domain;
	
	public Publisher() {
	}
	
	public Publisher(String name, String domain, String email, String password) {
		this.name = name;
		this.domain = domain;
		this.email = email;
		this.password = password;
		this.createDate = new Date();
	}

	public Publisher(String name, String domain) {
		this.name = name;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
}

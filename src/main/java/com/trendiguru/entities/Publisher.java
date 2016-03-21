package com.trendiguru.entities;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("publishers")
@Indexes(
    @Index(value = "domain", fields = @Field("domain"))
)
public class Publisher {
	@Id
	ObjectId id;
	String name;
	String domain;
	String username;
	String password;
	Date createDate;
	Date lastLoginDate;
	
	public Publisher(String name, String domain, String username, String password) {
		this.name = name;
		this.domain = domain;
		this.username = username;
		this.password = password;
		this.createDate = new Date();
	}

	public Publisher(String name, String domain) {
		this.name = name;
		this.domain = domain;
	}

	public ObjectId getId() {
		return id;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
}

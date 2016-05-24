package com.trendiguru.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;

@Entity("users")
/*
@Indexes(
    @Index(value = "domain", fields = @Field("domain"))
)
*/
public class Publisher extends BaseUser {
	String name;
	
	//TODO - make unique so User B cannot create an account with access to Domain A !
	@Indexed(options = @IndexOptions(unique = true))
	String domain;
	String pid;	//data-pid value in web form
	
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain.replace("www.", "");
	}
	
	/**
	 * No longer toLowerCase() so can manually add extra visuals via Kibana which generates the ES id via the title of the visual eg "Robs Shelter Top 20 Images",
	 * which becomes /Robs-Shelter-Top-20-Images
	 * 
	 * This is used when creating a visual or dashboard in ElasticSearch.  The name forms the prefix of the ES index name. Eg "digital-spy-" + "dashboard"
	 * @return
	 */
	public String getEncodedName() {
		//return this.name.toLowerCase().replace(" ", "-");
		return this.name.replace(" ", "-");
	}
	
	public String getEncodedHTMLName() {
		return this.name.replace(" ", "%20");
	}

	
	
}

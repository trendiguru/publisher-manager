package com.trendiguru.entities;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

@Entity("users")
/*
@Indexes(
    @Index(value = "domain", fields = @Field("domain"))
)
*/
public class User extends BaseUser {
	@Indexed(options = @IndexOptions(unique = true))
	String name;	//publisher's company name
	
	//TODO - make unique so User B cannot create an account with access to Domain A !
	//@Indexed(options = @IndexOptions(unique = true))
	//String domain;
	String pid;	//data-pid value in web form
	RoleEnum role;
	
	public User() {
	}
	
	/**
	 * Create Admin user 
	 * 
	 * @param name
	 * @param email
	 * @param password
	 */
	/*
	public User(String name, String email, String password) {
		this(name, "", email, password, RoleEnum.Admin);
	}
	*/
	/**
	 * Create Non admin user
	 * 
	 * @param name
	 * @param domain
	 * @param email
	 * @param password
	 * @param role
	 */
	public User(String companyName, String contactName, String contactEmail, String password, RoleEnum role) {
		this.name = companyName;
		this.contactName = contactName;
		this.email = contactEmail;
		this.password = password;
		this.createDate = new Date();
		this.role = role;
	}
/*
	public User(String name, String domain) {
		this.name = name;
		this.domain = domain;
	}
*/
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

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}
/*
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain.replace("www.", "");
	}
*/
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

	public boolean isAdmin() {
		return this.role.equals(RoleEnum.Admin);
	}
	
}

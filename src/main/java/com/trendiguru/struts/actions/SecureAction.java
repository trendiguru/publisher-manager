package com.trendiguru.struts.actions;

import com.trendiguru.entities.BaseUser;


public class SecureAction extends BaseAction implements UserAware {

	private BaseUser user;
	
	@Override
	public BaseUser getUser() {
		return this.user;
	}

	@Override
	public void setUser(BaseUser user) {
		this.user = user;
	}
	
	/**
	 * Fetch the static user that was put in the Session when the user logged in
	 */
	/*
	public AppUser getLoggedInUser() {
		return (AppUser)user;
	}
*/
}

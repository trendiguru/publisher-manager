package com.trendiguru.struts.actions;

import com.trendiguru.entities.BaseUser;
import com.trendiguru.struts.interceptors.AuthenticationInterceptor;

/**
 * {@link AuthenticationInterceptor} check is an action implements this interface and if so, it injects the {@link BaseUser}
 * 
 * @author Jeremy
 *
 */
public interface UserAware {
	public BaseUser getUser();
	public void setUser(BaseUser user);
}

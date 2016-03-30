package com.trendiguru.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



/**
 * 
 * @author Jeremy
 *
 */
public class RemoveHashFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(RemoveHashFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    public void destroy() {
    }

    /**
     * Facebook url must end with "/" or "?".  Facebook OAuth doesn't work well with "?" at the end.  So use a simple Facebook mapped-url
     * and forward it via this filter to the required Struts action.
     * <p>
     * NB in web.xml - any other servlet filters must have the <dispatcher> set for "REQUEST" and "FORWARD" in their filter mapping tags.
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    	
    	HttpServletRequest request = (HttpServletRequest) req;
    	
    	String qs = request.getQueryString();
    	String a = request.getServletPath();
    	System.out.println("asset: " + a);
    	System.out.println("qs: " + qs);
    	
    	
        chain.doFilter(req, res);
    }
}

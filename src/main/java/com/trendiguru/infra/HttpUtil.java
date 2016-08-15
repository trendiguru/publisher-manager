package com.trendiguru.infra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil {

	private static Logger log = Logger.getLogger(HttpUtil.class);
	
	public static String httpGetToKibana(String url, String queryString) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
    	
		
    	HttpUriRequest request = new HttpGet(url + "?" + queryString);
    	request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        	

    	
    	// add header
    	request.setHeader("Authorization", "Basic a2liYW5hYWRtaW46ZXhhbXBsZXVzZXI=");
    	//request.setHeader("Authorization", "Basic a2liYW5hYWRtaW46aG93ZHlkMDBkZWU=");
    	//request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    	//request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    	
    	//request.setHeader("Content-Type", "application/json;charset=UTF-8");
    	request.setHeader("Connection", "keep-alive");
    	//request.setHeader("kbn-version", "4.5");
    	request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
    	
    	CloseableHttpResponse response1 =null;
    	String resultBody = null;
    	
    	try {
			
    		response1 = httpclient.execute(request);
    			
    		    //System.out.println(response1.getStatusLine());
    		    if (response1.getStatusLine().getStatusCode() == 200) {
    		    	System.out.println(response1.getStatusLine());
    		    	//InputStream is = entity1.getContent();
        		    resultBody = EntityUtils.toString(response1.getEntity());
        		    //log.info(resultBody);
        		    // do something useful with the response body
        		    // and ensure it is fully consumed
        		    HttpEntity entity1 = response1.getEntity();
        		    EntityUtils.consume(entity1);
    		    } else {
    		    	log.fatal(response1);
    		    	System.out.println(response1);
    		    }
    		    
    		    log.info("------------------------------------");
    		    System.out.println("------------------------------------");
    		    
    		    //EntityUtils.consume(entity1);
    		
		} catch (UnsupportedEncodingException e) {
			log.fatal(e);
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			log.fatal(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.fatal(e);
			e.printStackTrace();
		}
    	finally {
		    try {
				response1.close();
			} catch (IOException e) {
				log.fatal(e);
				e.printStackTrace();
			}
		}
    	return resultBody;
    
	}
	
	public static String httpPostToKibana(String url, String postBody) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		
    	// add headers
    	post.setHeader("Authorization", "Basic a2liYW5hYWRtaW46ZXhhbXBsZXVzZXI=");   
    	//post.setHeader("Authorization", "Basic a2liYW5hYWRtaW46aG93ZHlkMDBkZWU=");
    	post.setHeader("kbn-version", "4.5.0");	
    	
    	StringBuffer result = null;
    	
    	try {
			//String json = getJSON(clickEvent, PID);
			StringEntity se = new StringEntity(postBody, ContentType.APPLICATION_JSON); 
			post.setEntity(se);

			HttpResponse response = client.execute(post);
			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	    	result = new StringBuffer();
	    	String line = "";
	    	while ((line = rd.readLine()) != null) {
	    		result.append(line);
	    	}

	    	return result.toString();
	    	
			
		} catch (UnsupportedEncodingException e) {
			log.fatal(e);
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			log.fatal(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.fatal(e);
			e.printStackTrace();
		}

    	return result.toString();
	}
	
}

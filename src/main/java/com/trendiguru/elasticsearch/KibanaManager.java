package com.trendiguru.elasticsearch;

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

import com.trendiguru.config.ConfigManager;
import com.trendiguru.entities.User;

abstract class KibanaManager {
	private static Logger log = Logger.getLogger(KibanaManager.class);
	private ConfigManager configManager = ConfigManager.getInstance();
	
	/* return the name of the entity eg Dashboard, Visual */
	abstract String getESEntityUrl(String encodedEntityName);
	
	/**
	 * Add a Kibana visual or dashboard
	 * 
	 * @param publisher
	 * @param encodedPostPayLoad - the JSON payload encoded
	 * @param encodedEntityName - eg Dashboard-DigitalSpy
	 **/
	public void add(User publisher, String encodedPostPayLoad, String encodedEntityName) {
    	//String url = "http://localhost:9000/elasticsearch/.kibana/visualization/" + visualName + "?op_type=create";
    	
    	
    	//String url = "http://localhost:9000/elasticsearch/_mget?timeout=0&ignore_unavailable=true&preference=1458207222316";

    	HttpClient client = HttpClientBuilder.create().build();
    	HttpPost post = new HttpPost("http://" + configManager.getKibanaDomain() + getESEntityUrl(encodedEntityName));

    	// add header
    	post.setHeader("Authorization", "Basic a2liYW5hYWRtaW46ZXhhbXBsZXVzZXI=");
    	//post.setHeader("Authorization", "Basic a2liYW5hYWRtaW46aG93ZHlkMDBkMzM=");
    	post.setHeader("Accept", "application/json, text/plain, */*");
    	//post.setHeader("Content-Type", "application/json;charset=UTF-8");
    	post.setHeader("Connection", "keep-alive");
    	
    	//TODO - response header, not request
    	post.setHeader("kbn-version", "4.5.0");	
    	
    	try {
			
			StringEntity se = new StringEntity(encodedPostPayLoad, ContentType.APPLICATION_JSON); 
			post.setEntity(se);
			//post.setEntity(new UrlEncodedFormEntity(urlParameters));
			
			HttpResponse response = client.execute(post);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	    	StringBuffer result = new StringBuffer();
	    	String line = "";
	    	while ((line = rd.readLine()) != null) {
	    		result.append(line);
	    	}
	    	//System.out.println(result);
	    	log.info(result);
	    	
	    	if (response.getStatusLine().getStatusCode() == 409) {
	    		//System.out.println("document_already_exists_exception !!");
	    		log.info("document_already_exists_exception !!");
	    	}
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
    }
	
	public String read(String uri, String method, String postBody) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
    	
    	HttpUriRequest request = null;
    	if (method.equals("POST")) {
    		//request = new HttpPost("http://localhost:9000/" + uri);
    		log.info("http://" + configManager.getKibanaDomain() + "/" + uri);
    		request = new HttpPost("http://" + configManager.getKibanaDomain() + "/" + uri);
    		
    		request.setHeader("Accept", "application/json, text/plain, */*");
    		request.setHeader("Content-Type", "application/json;charset=UTF-8");
    		request.setHeader("kbn-version", "4.5.0");
    		//request.setHeader("Origin", "http://localhost:9000");
    		
    		//request.setHeader("referer", "http://localhost:9000/app/kibana");
    		
        	
    		//System.out.println("url: http://localhost:9000/" + uri);
    		
    		//List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    		//nvps.add(new BasicNameValuePair("username", "vip"));
    		//nvps.add(new BasicNameValuePair("password", "secret"));
    		try {
				((HttpPost)request).setEntity(new StringEntity(postBody));
				System.out.println(postBody);
				System.out.println("post size: " + postBody.length());
			} catch (UnsupportedEncodingException e) {
				log.fatal(e);
				e.printStackTrace();
			}
    	} else {
    		log.info("http://" + configManager.getKibanaDomain() + "/" + uri);
    		//System.out.println("url: http://localhost:9000/" + uri);
    		request = new HttpGet("http://" + configManager.getKibanaDomain() + "/" + uri);
    		request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        	
    	}

    	
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
}

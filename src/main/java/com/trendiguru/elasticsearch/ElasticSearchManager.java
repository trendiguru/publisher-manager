package com.trendiguru.elasticsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.trendiguru.entities.Publisher;

abstract class ElasticSearchManager {
	
	/* return the name of the entity eg Dashboard, Visual */
	abstract String getESEntityUrl(String encodedEntityName);
	
	public void send(Publisher publisher, String encodedPostPayLoad, String encodedEntityName) {
    	//String url = "http://localhost:9000/elasticsearch/.kibana/visualization/" + visualName + "?op_type=create";
    	
    	
    	//String url = "http://localhost:9000/elasticsearch/_mget?timeout=0&ignore_unavailable=true&preference=1458207222316";

    	HttpClient client = HttpClientBuilder.create().build();
    	HttpPost post = new HttpPost(getESEntityUrl(encodedEntityName));

    	// add header
    	post.setHeader("Authorization", "Basic a2liYW5hYWRtaW46ZXhhbXBsZXVzZXI=");
    	post.setHeader("Accept", "application/json, text/plain, */*");
    	//post.setHeader("Content-Type", "application/json;charset=UTF-8");
    	post.setHeader("Connection", "keep-alive");
    	post.setHeader("kbn-version", "4.4.1");	
    	
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
	    	System.out.println(result);
	    	
	    	if (response.getStatusLine().getStatusCode() == 409) {
	    		System.out.println("document_already_exists_exception !!");
	    	}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

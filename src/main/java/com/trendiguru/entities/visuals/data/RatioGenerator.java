package com.trendiguru.entities.visuals.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendiguru.config.ConfigManager;
import com.trendiguru.entities.User;
import com.trendiguru.infra.HttpUtil;
import com.trendiguru.infra.JsonFactory;
import com.trendiguru.mongodb.MorphiaManager;

/**
 * Kibana cannot provide a metric visual that shows a ratio between 2 values
 * {@linkhttps://discuss.elastic.co/t/find-ratio-between-two-metrics/52153/10}
 * 
 * But I can query ES using the Sense tool by querying using a "scripted_metric" JSON
 * {@link https://github.com/elastic/kibana/issues/2646}
 * 
 * This returns JSON with the calculated ratio value which I will then send to ES as a
 * new request "ratio_our_button_clicked", "ratio_item_clicked".
 * 
 * In Kibana, I can then create a metric that displays this injected value.
 * 
 * This tool needs to inject the value each hour for each publisher.
 * 
 * Domains are discovered for each PID in our Mongo DB.  The 2 ratios are then calculated for each domain:
 * - CTR on our icon
 * - CTR on a resulting item
 * 
 * A crontab job calls this code every hour, every day.
 * 
 * @author Jeremy
 *
 */
public class RatioGenerator {

	private static Logger log = Logger.getLogger(RatioGenerator.class);
	private ConfigManager configManager = ConfigManager.getInstance();
	
	//TODO - change to production mode
	//
	//
	//
	public static String MODE = "test";
	public static String EVENT_CLICK_ON_OUR_BUTTON = "Trendi%20Button%20Clicked";
	public static String EVENT_CLICK_ON_RESULT_ITEM = "Result%20Clicked";
	
	public static String RATIO_EVENT_CLICK_ON_OUR_BUTTON = "Ratio%20Trendi%20Button%20Clicked";
	public static String RATIO_EVENT_CLICK_ON_RESULT_ITEM = "Ratio%20Result%20Clicked";
	
	public static void main( String[] args ) {
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");	//index format for ES
		Date today = Calendar.getInstance().getTime();
		String indexDate = df.format(today);
		log.info("Date: " + indexDate);
		new RatioGenerator(indexDate);
    }
	
	public RatioGenerator(String indexDate) {
		
		try {
			addLetsEncryptCert();
		} catch (Exception e1) {
			log.fatal(e1, e1);
		}
		
		MorphiaManager morphiaManager = MorphiaManager.getInstance();
		List<User> publisherList = morphiaManager.findAll();
		for (User publisher : publisherList) {
			log.info("PID: " + publisher.getPid() + ", Name: " + publisher.getName());
			
			List<String> domainsPerPIDList = getPublisherDomainsPerPID(publisher.getPid(), indexDate);
			if (domainsPerPIDList.isEmpty()) {
				log.info("- No events with a domain for this PID in ES!");
			} else {
				for (String publisherDomain : domainsPerPIDList) {
					
					String ourButtonClickRatio = getRatio(publisherDomain, "2016.07.12", EVENT_CLICK_ON_OUR_BUTTON);
					injectRatioIntoElasticSearch(ourButtonClickRatio, RATIO_EVENT_CLICK_ON_OUR_BUTTON, publisher.getPid(), publisherDomain);
					
					String resultItemButtonClickRatio = getRatio(publisherDomain, "2016.07.12", EVENT_CLICK_ON_RESULT_ITEM);
					injectRatioIntoElasticSearch(resultItemButtonClickRatio, RATIO_EVENT_CLICK_ON_RESULT_ITEM, publisher.getPid(), publisherDomain);
					
				}
			}
			log.info("-------------------");
			
		}
		
	}
	
	public List<String> getPublisherDomainsPerPID(String PID, String indexDate) {
		
		String queryString = "uri=http%3A%2F%2F" + URLEncoder.encode(configManager.getSenseDomain()) + "%2Flogstash-" + indexDate + "%2F_search%3Fsearch_type%3Dcount";
		String url = "http://" + configManager.getKibanaDomain() + "/api/sense/proxy?" + queryString;
		
		String result = HttpUtil.httpPostToKibana(url, getDomainsPerPIDJSON(PID));
		
		List<String> domainList = new ArrayList<String>();
		
		JsonFactory jsonFactory = JsonFactory.getInstance();
		ObjectMapper mapper = jsonFactory.getMapper();
		
		try {
			JsonNode configJson = mapper.readTree(result.toString());
			
			if (configJson.get("status") == null) {
				Iterator<JsonNode> itr = configJson.get("aggregations").get("domains").get("buckets").elements();
				while(itr.hasNext()) {
					JsonNode element = itr.next();
					domainList.add(element.get("key").textValue());
			        //System.out.print(element + " ");
			    }
				//log.info("Publisher PID: " + PID + ", ratio: " + ratio);
				/*
				if (response.getStatusLine().getStatusCode() == 409) {
		    		//System.out.println("document_already_exists_exception !!");
		    		log.info("document_already_exists_exception !!");
		    	}
				*/
			} else {
				int statusCode = configJson.get("status").asInt();
				log.error("Publisher PID: " + PID + " - Sense returned an error, status code: " + statusCode);
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
		
		return domainList;
	}
	
	/**
	 * @param indexDate eg YYYY.MM.DD eg 2016.06.07
	 * @clickEvent eg ????????
	 */
	public String getRatio(String publisherDomain, String indexDate, String clickEvent) {
		String queryString = "uri=http%3A%2F%2F" + URLEncoder.encode(configManager.getSenseDomain()) + "%2Flogstash-" + indexDate + "%2F_search%3Fsearch_type%3Dcount";
		String url = "http://" + configManager.getKibanaDomain() + "/api/sense/proxy?" + queryString;
		
		String json = getRatioPerPublisherDomainJSON(clickEvent, publisherDomain);
		String result = HttpUtil.httpPostToKibana(url, json);
		
		String ratio = null;
    	
    	try {
			
	    	JsonFactory jsonFactory = JsonFactory.getInstance();
			ObjectMapper mapper = jsonFactory.getMapper();
			
			JsonNode configJson = mapper.readTree(result.toString());
			
			if (configJson.get("status") == null) {
				ratio = configJson.get("aggregations").get("ratio").get("value").asText();
				log.info("Publisher Domain: " + publisherDomain + ", ratio: " + ratio);
				/*
				if (response.getStatusLine().getStatusCode() == 409) {
		    		//System.out.println("document_already_exists_exception !!");
		    		log.info("document_already_exists_exception !!");
		    	}
				*/
			} else {
				int statusCode = configJson.get("status").asInt();
				log.error("Publisher Domain: " + publisherDomain + " - Sense returned an error, status code: " + statusCode);
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

    	return ratio;
	}
	
	
	//@deprecated
	//2. inject ratio into ES
	/*
	public void injectRatioIntoElasticSearch(String ratio, String ratioEvent, String PID, String publisherDomain) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
    	
		//log.info("http://" + configManager.getKibanaDomain() + "/" + uri);
		//System.out.println("url: http://localhost:9000/" + uri);
		int rv = (int)(Math.random() * 1000000000);
				
		HttpUriRequest request = new HttpGet("https://track.trendi.guru/tr/test?PID=" + PID + "&event=" + ratioEvent + "&ratio=" + ratio + "&publisherDomain=" + publisherDomain + "&rv=" + rv);        	

    	CloseableHttpResponse response1 =null;
        	
    	try {
			
    		response1 = httpclient.execute(request);
    			
		    //System.out.println(response1.getStatusLine());
		    if (response1.getStatusLine().getStatusCode() == 200) {
		    	log.info("Successfully sent ratio of " + ratio + " to be ingested by ngninx.");
		    } else {
		    	log.fatal(response1);
		    	System.out.println(response1);
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
    	finally {
		    try {
				response1.close();
			} catch (IOException e) {
				log.fatal(e);
				e.printStackTrace();
			}
		}
    	
	}
	*/
	
	/**
	 * Please note this fetches a ratio per PID.  If a PID receives events from multiple domains, the ratio per domain is unknown!
	 * @param clickEvent
	 * @param PID
	 * @return
	 */
	/*
	public String getRatioPerPIDJSON(String clickEvent, String PID) {
		return "{" +
	
		  "\"query\" : {" +
		    "\"match\" : {" +
		      "\"PID\": \"" + PID + "\"" +
		    "}" +
		  "}," +
			  
		  "\"aggs\": {" +
		    "\"ratio\": {" +
		      "\"scripted_metric\": {" +
		        "\"init_script\": \"_agg['click'] = _agg['all'] = 0\"," +
		        "\"map_script\" : \"if (doc['event.raw'].value == \\\"" + clickEvent + "\\\") { _agg['click'] +=1 }; _agg['all'] +=1\"," +
		        "\"reduce_script\": \"clicks = all = 0; for (agg in _aggs) {  clicks += agg.click ; all += agg.all ;}; return 100 * clicks / all\"" +

		      "}" +
		    "}" +
		  "}" +
		"}";
	}
	*/
	
	public String getRatioPerPublisherDomainJSON(String clickEvent, String publisherDomain) {
		return "{" +
	
		  "\"query\" : {" +
		    "\"match\" : {" +
		      "\"publisherDomain.raw\": \"" + publisherDomain + "\"" +
		    "}" +
		  "}," +
			  
		  "\"aggs\": {" +
		    "\"ratio\": {" +
		      "\"scripted_metric\": {" +
		        "\"init_script\": \"_agg['click'] = _agg['all'] = 0\"," +
		        "\"map_script\" : \"if (doc['event.raw'].value == \\\"" + clickEvent + "\\\") { _agg['click'] +=1 }; _agg['all'] +=1\"," +
		        "\"reduce_script\": \"clicks = all = 0; for (agg in _aggs) {  clicks += agg.click ; all += agg.all ;}; return 100 * clicks / all\"" +

		      "}" +
		    "}" +
		  "}" +
		"}";
	}
	
	public String getDomainsPerPIDJSON(String PID) {
		return "{" +
			"\"size\": 0," +
			"\"query\": {" +
				"\"filtered\": {" +
					"\"query\": {" +
						"\"query_string\": {" +
							"\"query\": \"PID:" + PID + "\"," +
							"\"analyze_wildcard\": true" +
						"}" +
					"}" +
				"}" +
			"}," +
			"\"aggs\": {" +
				"\"domains\": {" +
					"\"terms\": {" +
						"\"field\": \"publisherDomain.raw\"," +
						"\"size\": 5," +
						"\"order\": {" +
							"\"_count\": \"desc\"" +
						"}" +
					"}" +
				"}" +
			"}" +
		"}";
	}
	
	/**
	 * {@link http://stackoverflow.com/questions/34110426/does-java-support-lets-encrypt-certificates}
	 */
	public void addLetsEncryptCert() {
		try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            Path ksPath = Paths.get(System.getProperty("java.home"),
                    "lib", "security", "cacerts");
            keyStore.load(Files.newInputStream(ksPath),
                    "changeit".toCharArray());

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            try (InputStream caInput = new BufferedInputStream(
                    // this files is shipped with the application
                    SSLExample.class.getResourceAsStream("/com/trendiguru/letsencrypt/lets-encrypt-x3-cross-signed.der"))) {
                Certificate crt = cf.generateCertificate(caInput);
                System.out.println("Added Cert for " + ((X509Certificate) crt)
                        .getSubjectDN());

                keyStore.setCertificateEntry("DSTRootCAX3", crt);
            }

            if (false) { // enable to see
                System.out.println("Truststore now trusting: ");
                PKIXParameters params = new PKIXParameters(keyStore);
               
                Iterator it = params.getTrustAnchors().iterator();
                while( it.hasNext() ) {
                    TrustAnchor ta = (TrustAnchor)it.next();
                    // Get certificate
                    X509Certificate cert = ta.getTrustedCert();
                    System.out.println(cert);
                }
                /*
                params.getTrustAnchors().stream().map(TrustAnchor::getTrustedCert)
                        .map(X509Certificate::getSubjectDN)
                        .forEach(System.out::println);
                System.out.println();
                */
            }

            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public void injectRatioIntoElasticSearch(String ratio, String ratioEvent, String PID, String publisherDomain) {
		int rv = (int)(Math.random() * 1000000000);
		URL url = null;
		try {
			url = new URL("https://track.trendi.guru/tr/test?PID=" + PID + "&event=" + ratioEvent + "&ratio=" + ratio + "&publisherDomain=" + publisherDomain + "&rv=" + rv);
		
			/* this works with the newly added lets-encrypt cert.  But the Apache HttpClient does not work with it! */
        	URLConnection connection = url.openConnection();
        
            connection.connect();
            log.info("Sent ratio to ES: " + url);
            System.out.println("Headers of " + url + " => " + connection.getHeaderFields());
        } catch (SSLHandshakeException e) {
            System.out.println("Untrusted: " + url);
        } catch (IOException e) {
        	System.out.println(e.getStackTrace());
        } 
    }
}

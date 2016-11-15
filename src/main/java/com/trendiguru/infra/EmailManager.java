package com.trendiguru.infra;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trendiguru.config.ConfigManager;
import com.trendiguru.entities.User;

public class EmailManager {

	private static Logger log = Logger.getLogger(EmailManager.class); 
	
	private static ConfigManager configManager = ConfigManager.getInstance();

	public static void forgottenPassword(User publisher) {
		String resetLink = configManager.getTomcatDomain() + "/publisher-manager/auth/resetPasswordForm?passwordResetToken=" + publisher.getPasswordResetToken();
		StringBuilder sb = new StringBuilder();
		sb.append("Hi " + publisher.getContactName() + ",").append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
		sb.append("You requested to have your password reset for your Trendi Guru Analytics account.  So please click on this reset link:").append(System.getProperty("line.separator"));
		sb.append(resetLink);
		
		send(publisher.getEmail(), publisher.getName(), "TrendiGuru password reset request for " + publisher.getName(), sb.toString());
		//send("jscolton@gmail.com", "Jeremy Colton", "New Publisher SignUp - " + publisher.getDomain(), sb.toString());
	}
	
	public static void passwordReset(User publisher, String newUnhashedPassword) {
		StringBuilder sb = new StringBuilder();
		sb.append("Hi " + publisher.getContactName() + ",").append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
		sb.append("You password was successfully reset!").append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
		sb.append("Login address: http://publisher.trendi.guru").append(System.getProperty("line.separator"));
		sb.append("Username: " + publisher.getEmail()).append(System.getProperty("line.separator"));
		sb.append("Password: " + newUnhashedPassword).append(System.getProperty("line.separator"));
		
		send(publisher.getEmail(), publisher.getName(), "TrendiGuru password reset successful for " + publisher.getName(), sb.toString());
		//send("jscolton@gmail.com", "Jeremy Colton", "New Publisher SignUp - " + publisher.getDomain(), sb.toString());
	}
	
	public static void newSignUpNotifyTrendiGuru(User publisher) {
		StringBuilder sb = new StringBuilder();
		sb.append("Publisher Name: " + publisher.getName()).append(System.getProperty("line.separator"));
		sb.append("Publisher Contact Name: " + publisher.getContactName()).append(System.getProperty("line.separator"));
		sb.append("Publisher Contact Email: " + publisher.getEmail()).append(System.getProperty("line.separator"));
		
		ArrayNode emailArrayNode = ConfigManager.getInstance().getEmails();
		Iterator<JsonNode> emailIterator = emailArrayNode.elements();
		while (emailIterator.hasNext()) {
		    JsonNode emailNode = emailIterator.next();
		    send(emailNode.get("email").textValue(), emailNode.get("name").textValue(), "New Publisher SignUp - " + publisher.getName(), sb.toString());
		}
		
		//send("jscolton@gmail.com", "Jeremy Colton", "New Publisher SignUp - " + publisher.getDomain(), sb.toString());
	}
	
	public static void newSignUpNotifyPublisher(User publisher) {
		StringBuilder sb = new StringBuilder();
		sb.append("Login address: http://publisher.trendi.guru").append(System.getProperty("line.separator"));
		sb.append("Username: " + publisher.getEmail()).append(System.getProperty("line.separator"));
		sb.append("Password: " + publisher.getRepeatPassword()).append(System.getProperty("line.separator"));
		sb.append("Company Name: " + publisher.getName()).append(System.getProperty("line.separator"));
		sb.append("Integration code (place in <head> of your site): <script type='text/javascript' id='fzz-script' data-pid='" + publisher.getPid() + "' src='https://fzz.storage.googleapis.com/fzz.min.js'></script>");
			
		
		send(publisher.getEmail(), publisher.getName(), "Trendi Guru SignUp - Dashboard Login Info for '" + publisher.getName() + "'", sb.toString());
	}
	
	public static void send(String toEmail, String toName, String subject, String msgBody) {
		
		Properties props = new Properties();
		
		Session session = null;
		
		if (ConfigManager.getInstance().isLocal()) {
			log.info("Sending email locally...");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.starttls.enable",true);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.port", "587");
			
			session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("jay@trendiguru.com", "xxx");
						}
					  });
			
		} else {
		
			log.info("Sending email in production...");
			props.put("mail.smtp.host", "smtp-relay.gmail.com");	//this will only work from the publisher-manager compute engine since it's an accepted domain for the gmail relay.  Cannot run this on localhost
			props.put("mail.smtp.port", "587");
			//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.starttls.enable","true");	//not boolean!!!
			props.put("mail.smtp.auth", "false");
			props.put("mail.smtp.ssl.trust", "smtp-relay.gmail.com");
			//props.put("smtp.ClientDomain","trendiguru.com");
			
			
			session = Session.getDefaultInstance(props);
		}
		
		
		//Session session = Session.getDefaultInstance(props, null);

		//String msgBody = "Sending email using JavaMail API…";

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("Notifier@trendiguru.com", "TrendiGuru - NoReply"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toName));
			msg.setSubject(subject);
			msg.setText(msgBody);
			Transport.send(msg);
			log.info("4. Email sent successfully to: " + toEmail);

		} catch (AddressException e) {
			log.fatal(e);
			//throw new RuntimeException(e);
		} catch (MessagingException e) {
			log.fatal(e);
			//throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.fatal(e);
			//throw new RuntimeException(e);
			//e.printStackTrace();
		}	
		
		
	}
}

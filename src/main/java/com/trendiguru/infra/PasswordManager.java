package com.trendiguru.infra;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;




/**
 * A simple random password generator, user can specify the length.
 * Password randomly consists of 0-9, a-z, A-Z 
 * <p>
 * {@see http://bobcat.webappcabaret.net/javachina/jc/share/PwGen.htm}
 * 
 * Also, when encrypting a password, this class provides methods to:
 * a) Generate a random SALT using the Java's own secure random number geneator
 * b) Uses SHA-256 (set by the calling class) which generates a 32-byte long cryptographic hash
 * c) uses base 64 to encode the resulting hash.
 * 
 * The above is recommended by https://crackstation.net/hashing-security.htm
 */

public class PasswordManager {

	private static final int PASSWORD_LENGTH = 8;
	
	public static String resetPassword() {
		return getRandomAlphaPassword(PASSWORD_LENGTH);
	}
	
	public static String getRandomPassword(int n) {
		return getRandomPassword(n, false);
	}
	
	public static String getRandomAlphaPassword(int n) {
		return getRandomPassword(n, true);
	}	
		
	public static String getRandomPassword(int n, boolean alphaOnly) {
		char[] pw = new char[n];
		int c = 'A';
		int r1 = 0;
		
		int rangeLimit = alphaOnly ? 2 : 3; 
		
		for (int i = 0; i < n; i++) {
			r1 = (int) (Math.random() * rangeLimit);
			switch (r1) {
			case 0:
				c = 'A' + (int) (Math.random() * 26);
				break;
			case 1:
				c = 'a' + (int) (Math.random() * 26);
				break;
			case 2:
				c = '0' + (int) (Math.random() * 10);
				break;
			}
			pw[i] = (char) c;
		}
		return new String(pw);
	}
/*
	public static String SHA1(String plaintext)	{
		
		// see http://stackoverflow.com/questions/4400774/java-calculate-a-sha1-of-a-string		
		MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
		DigestUtils.sha256(plaintext);
		String hash2 = encoder.encodePassword(plaintext, "there once was a lad called Bob who plane table 234222 tree pin wivvle");
		return hash2;
		*/
		
		
		/*
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(plaintext.getBytes("UTF-8"));
		byte raw[] = md.digest();
		String hash = (new BASE64Encoder()).encode(raw);
		return hash;
		*/
	//}
	
	public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }
	
	public static String bytetoString(byte[] input) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(input);
    }

	public static byte[] getHashWithSalt(String input, String technique, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(technique);
        digest.reset();
        digest.update(salt);
        byte[] hashedBytes = digest.digest(stringToByte(input));
        return hashedBytes;
    }
	
	public static byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);

        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }
	/*
	public static void main(String[] args) {
		
			System.out.println(SHA1("1234"));
			System.out.println(SHA1("1234"));
			System.out.println(SHA1("1234"));
			System.out.println(SHA1("1234"));
			System.out.println(SHA1("1234"));
		
	}
	*/
}

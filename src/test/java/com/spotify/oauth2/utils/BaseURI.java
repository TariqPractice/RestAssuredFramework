package com.spotify.oauth2.utils;

public class BaseURI {

	static String  URI="https://api.spotify.com";
	static String Account_URI="https://accounts.spotify.com";

	public static String getBaseURI() {
		// Check if BASEURI is provided as a system property 
		String baseUri = System.getProperty("BASE_URI"); 
		if (baseUri != null && !baseUri.isEmpty()) {
						return baseUri;
		}else {
			return URI;
		}
	}
	
	public static String getAccountBaseURI() {
		// Check if BASEURI is provided as a system property 
		String baseUri = System.getProperty("ACCOUNT_BASE_URI"); 
		if (baseUri != null && !baseUri.isEmpty()) {
				return baseUri;
		}else {
			return Account_URI;
		}
	}


}

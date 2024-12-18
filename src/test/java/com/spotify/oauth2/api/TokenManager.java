package com.spotify.oauth2.api;

import java.time.Instant;
import java.util.HashMap;

import com.spotify.oauth2.utils.ConfigLoader;

import io.restassured.response.Response;

public class TokenManager {
	
	private static String access_Token;
	private static Instant expiry_time;
	
	public synchronized static String getToken() {//to avoid rest condition in parallel execution so that another thread can wait
		try {
			if(access_Token == null || Instant.now().isAfter(expiry_time)) {
				System.out.println("Renewing Token..");
				Response response= renewToken();
				access_Token = response.path("access_token");
				int expiryDurationInSeconds= response.path("expires_in");
				expiry_time= Instant.now().plusSeconds(expiryDurationInSeconds-300);
			}else {
				System.out.println("Token is good to use");
			}
			

		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ABORT!!! Failed to get token");
		}
		
		return access_Token;
		
	}
	
	private static Response renewToken() {
		HashMap<String,String> formParams= new HashMap<String,String>();
//		formParams.put("client_id", "a67da5d057d84f4d91e8fe5dc057977d");
//		formParams.put("client_secret", "c5f02866a18b4b4ab353006308b0ced6");
//		formParams.put("grant_type", "refresh_token");
//		formParams.put("refresh_token", "AQCihp1wbRnAQTWvt2CW4QZHUjfHgAxQuo0dsPHa7ATR9siu0M8tmVn__E_SvbpRQA6i5NGN4JCyH5OlhsteNR0Xal1lAI7OSXVbrmNR4xIE1zL7Ln93F3gThw6w527OdWw");
		
		formParams.put("client_id", ConfigLoader.getInstance().getClientId());
		formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
		formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
		formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
		
		Response response= RestResource.postAccount(formParams);
		
		if(response.statusCode()!=200) {
			throw new RuntimeException("ABORT!!! Renew token failed");
		}else {
			return response;
		}
	}

}

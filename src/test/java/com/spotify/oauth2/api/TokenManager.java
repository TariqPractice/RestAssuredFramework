package com.spotify.oauth2.api;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TokenManager {
	
	public static String renewToken() {
		HashMap<String,String> formParams= new HashMap<String,String>();
		formParams.put("client_id", "a67da5d057d84f4d91e8fe5dc057977d");
		formParams.put("client_secret", "c5f02866a18b4b4ab353006308b0ced6");
		formParams.put("grant_type", "refresh_token");
		formParams.put("refresh_token", "AQCihp1wbRnAQTWvt2CW4QZHUjfHgAxQuo0dsPHa7ATR9siu0M8tmVn__E_SvbpRQA6i5NGN4JCyH5OlhsteNR0Xal1lAI7OSXVbrmNR4xIE1zL7Ln93F3gThw6w527OdWw");
		
		Response response= given().
			baseUri("https://accounts.spotify.com").//since base uri is different from request specification
			contentType(ContentType.URLENC).
			formParams(formParams).
			
		when().
			post("/api/token").
		then().spec(SpecBuilder.getResponseSpec()).extract().response();
		
		if(response.statusCode()!=200) {
			throw new RuntimeException("ABORT!!! Renew token failed");
		}else {
			return response.path("access_token");
		}
	}

}

package com.spotify.oauth2.api;

import static io.restassured.RestAssured.given;
import static com.spotify.oauth2.api.Route.*;
import java.util.HashMap;
import io.restassured.response.Response;

public class RestResource {
	
	public static Response post(String path, String token,Object requestPlaylist) {
		return given(SpecBuilder.getRequestspec()).
					auth().oauth2(token).
//					header("Authorization", "Bearer "+token).
					body(requestPlaylist).
				when().post(path).
				then().spec(SpecBuilder.getResponseSpec()).
					extract().
					response();
	}
	
	public static Response postAccount(HashMap<String,String> formParams) {
		return given(SpecBuilder.getAccountRequestspec()).
				formParams(formParams).		
			when().
				post(API+TOKEN).
			then().spec(SpecBuilder.getResponseSpec()).
				extract().
				response();

	}
	
	public static Response get(String path, String token) {
		return given(SpecBuilder.getRequestspec()).
				auth().oauth2(token).
//				header("Authorization", "Bearer "+token).
		when().get(path).
		then().spec(SpecBuilder.getResponseSpec()).
				assertThat().
				statusCode(200).
				extract().
				response();
	}
	
	public static Response update(String path,String token ,Object requestPlaylist) {
		return given(SpecBuilder.getRequestspec()).
					auth().oauth2(token).
//					header("Authorization", "Bearer "+token).
					body(requestPlaylist).
				when().
					put(path).
				then().spec(SpecBuilder.getResponseSpec()).
					extract().
					response();
	}
	

}

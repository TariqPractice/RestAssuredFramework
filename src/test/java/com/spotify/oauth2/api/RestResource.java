package com.spotify.oauth2.api;

import static io.restassured.RestAssured.given;

import com.spotify.oauth2.api.SpecBuilder;
import com.spotify.oauth2.pojo.Playlist;

import io.restassured.response.Response;

public class RestResource {
	
	public static Response post(String path, String token,Object requestPlaylist) {
		return given(SpecBuilder.getRequestspec()).
					header("Authorization", "Bearer "+token).
					body(requestPlaylist).
				when().post(path).
				then().spec(SpecBuilder.getResponseSpec()).
					extract().
					response();
	}
	
	public static Response get(String path, String token) {
		return given(SpecBuilder.getRequestspec()).
				header("Authorization", "Bearer "+token).
		when().get(path).
		then().spec(SpecBuilder.getResponseSpec()).
				assertThat().
				statusCode(200).
				extract().
				response();
	}
	
	public static Response update(String path,String token ,Object requestPlaylist) {
		return given(SpecBuilder.getRequestspec()).
					header("Authorization", "Bearer "+token).
					body(requestPlaylist).
				when().
					put(path).
				then().spec(SpecBuilder.getResponseSpec()).
					extract().
					response();
	}
	

}

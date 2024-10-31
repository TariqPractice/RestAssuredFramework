package com.spotify.oauth2.api.applicationApi;

import static io.restassured.RestAssured.given;

import com.spotify.oauth2.api.SpecBuilder;
import com.spotify.oauth2.pojo.Playlist;

import io.restassured.response.Response;

public class PlaylistApi {

	static String accessToken="BQBF9MHXEsTffJ6xu8aMlIAG1TACGIANKwkmLutW667_qae_p77qCkit-K4UEWXSie1ZjBanqprP6zWdcGPngXT2psFBntH9x5NUJg5FnxC8BjhOinKSvjEagYQe5uxwV1ezUYSuMSwk3Zquyz2WcidFGZF3NmfNT9sk5gMJOCtUCstYVWZgy9eA3P-qbHKCH6yhCZk-sojzmIjqDJrw9HrbjzipAYAAPPl6HuQktuD42hl-jUmXT_78zy3Dfbu37Si-LfQwHtPbhozyDMTvXwTU";
	
	public static Response post(Playlist requestPlaylist) {
		return given(SpecBuilder.getRequestspec()).
					header("Authorization", "Bearer "+accessToken).
					body(requestPlaylist).
				when().post("/users/31riagxrmqgrxbxp3og5lon5zvvm/playlists").
				then().spec(SpecBuilder.getResponseSpec()).
					extract().
					response();
	}
	
	public static Response post(String token,Playlist requestPlaylist) {
		return given(SpecBuilder.getRequestspec()).
					body(requestPlaylist).
					header("Authorization","Bearer "+token).
				when().post("/users/31riagxrmqgrxbxp3og5lon5zvvm/playlists").
				then().spec(SpecBuilder.getResponseSpec()).
					extract().
					response();
	}
	
	public static Response get(String playlistId) {
		return given(SpecBuilder.getRequestspec()).
				header("Authorization", "Bearer "+accessToken).
		when().get("/playlists/"+playlistId).
		then().spec(SpecBuilder.getResponseSpec()).
				assertThat().
				statusCode(200).
				extract().
				response();
	}
	
	public static Response update(String playlistId,Playlist requestPlaylist) {
		return given(SpecBuilder.getRequestspec()).
					header("Authorization", "Bearer "+accessToken).
					body(requestPlaylist).
				when().
					put("/playlists/"+playlistId).
				then().spec(SpecBuilder.getResponseSpec()).
					extract().
					response();
	}
	

}

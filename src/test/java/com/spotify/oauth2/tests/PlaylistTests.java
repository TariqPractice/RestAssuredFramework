package com.spotify.oauth2.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.spotify.oauth2.api.SpecBuilder;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;

import io.restassured.http.ContentType;

public class PlaylistTests {
	
	@Test
	public void ShouldBeAbleToCreatePlaylist() {
		
		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName("New Playlist");
		requestPlaylist.setDescription("New playlist description");
		requestPlaylist.setPublic(false);
		
		Playlist responsePlaylist= given(SpecBuilder.getRequestspec()).
				body(requestPlaylist).
		when().post("/users/31riagxrmqgrxbxp3og5lon5zvvm/playlists").
		then().spec(SpecBuilder.getResponseSpec()).
				assertThat().
				statusCode(201).
				extract().
				response().
				as(Playlist.class);
		
		assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.getPublic(),equalTo(requestPlaylist.getPublic()));
	}

	@Test
	public void ShouldBeAbleToGetPlaylist() {
		
		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName("Updated Playlist Name");
		requestPlaylist.setDescription("Updated playlist description");
		requestPlaylist.setPublic(true);

		Playlist responsePlaylist = given(SpecBuilder.getRequestspec()).
		when().get("/playlists/1WTK34AP0Wr9rNSytCE7iC").
		then().spec(SpecBuilder.getResponseSpec()).
				assertThat().
				statusCode(200).
				extract().
				response().
				as(Playlist.class);

		assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.getPublic(),equalTo(requestPlaylist.getPublic()));
		
	}
	
	@Test
	public void ShouldBeAbleToUpdatePlaylist() {
//builder pattern created for pojo
		Playlist requestPlaylist = new Playlist().
				setName("New Playlist").
				setDescription("New playlist description").
				setPublic(false);
		
		
		given(SpecBuilder.getRequestspec()).
			body(requestPlaylist).
		when().
			put("/playlists/2p7cquNGGq2UXNNL2BWxBQ").
		then().spec(SpecBuilder.getResponseSpec()).
				assertThat().
				statusCode(200);
	}
	
	//Negative test cases
	@Test
	public void ShouldNotBeAbleToCreatePlaylistWithoutName() {
		
		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName("");
		requestPlaylist.setDescription("New playlist description");
		requestPlaylist.setPublic(false);
		
		Error error= given(SpecBuilder.getRequestspec()).
				body(requestPlaylist).
		when().post("/users/31riagxrmqgrxbxp3og5lon5zvvm/playlists").
		then().spec(SpecBuilder.getResponseSpec()).
				assertThat().
				statusCode(400).extract().response().as(com.spotify.oauth2.pojo.Error.class);
				
		assertThat(error.getError().getStatus(),equalTo(400));
		assertThat(error.getError().getMessage(),equalTo("Missing required field: name"));
	}

	@Test
	public void ShouldNotBeAbleToCreatePlaylistWithExpiredToken() {
		
		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName("New Playlist");
		requestPlaylist.setDescription("New playlist description");
		requestPlaylist.setPublic(false);
		
		Error error= given().
				baseUri("https://api.spotify.com").
				basePath("/v1").
				header("Authorization", "Bearer "+"dummyvalue12345").
				contentType(ContentType.JSON).
				log().all().
				body(requestPlaylist).
		when().post("/users/31riagxrmqgrxbxp3og5lon5zvvm/playlists").
		then().spec(SpecBuilder.getResponseSpec()).
				assertThat().
				statusCode(401).extract().response().as(Error.class);

		
		assertThat(error.getError().getStatus(),equalTo(401));
		assertThat(error.getError().getMessage(),equalTo("Invalid access token"));
	}
}

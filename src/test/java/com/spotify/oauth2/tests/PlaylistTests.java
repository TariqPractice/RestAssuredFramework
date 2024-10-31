package com.spotify.oauth2.tests;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static io.restassured.RestAssured.*;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.pojo.Error;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PlaylistTests {
	
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	String accessToken="BQCnwp9IzzeDR1AIbycj4ZWr1OFU2BwYSUL5j8iLUCp5IkpVmBh95eeenDDtDMywFuz_ZfpKwg6hqNsdWCExa6I9jZsE9Ck3kQOr9ZT5s7tNCtVvz2wAcJsxSdusJ2ibObfLWx3WYuPDHing1f9CwOISdI4tTpPoG-pz0uRHhJLrbr7rx9DbHIpaWBJwFxbfmJXEYiJ5HcN6AmM0I-aiUUhFxbts5eVHGqj6juBBaHxFJyPpmM3bOxr666vdKxQOTtGUakR-Adimj4oEHhFuQURC";
	
	@BeforeClass
	public void beforeClass() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
									setBaseUri("https://api.spotify.com").
									setBasePath("/v1").
									addHeader("Authorization", "Bearer "+accessToken).
									setContentType(ContentType.JSON).log(LogDetail.ALL);
		requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
									log(LogDetail.ALL);
		responseSpecification = responseSpecBuilder.build();
	}

	@Test
	public void ShouldBeAbleToCreatePlaylist() {
		
		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName("New Playlist");
		requestPlaylist.setDescription("New playlist description");
		requestPlaylist.setPublic(false);
		
		Playlist responsePlaylist= given(requestSpecification).
				body(requestPlaylist).
		when().post("/users/31riagxrmqgrxbxp3og5lon5zvvm/playlists").
		then().spec(responseSpecification).
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

		Playlist responsePlaylist = given(requestSpecification).
		when().get("/playlists/1WTK34AP0Wr9rNSytCE7iC").
		then().spec(responseSpecification).
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

		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName("New Playlist");
		requestPlaylist.setDescription("New playlist description");
		requestPlaylist.setPublic(false);
		
		
		given(requestSpecification).
			body(requestPlaylist).
		when().
			put("/playlists/2p7cquNGGq2UXNNL2BWxBQ").
		then().spec(responseSpecification).
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
		
		Error error= given(requestSpecification).
				body(requestPlaylist).
		when().post("/users/31riagxrmqgrxbxp3og5lon5zvvm/playlists").
		then().spec(responseSpecification).
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
		then().spec(responseSpecification).
				assertThat().
				statusCode(401).extract().response().as(Error.class);

		
		assertThat(error.getError().getStatus(),equalTo(401));
		assertThat(error.getError().getMessage(),equalTo("Invalid access token"));
	}
}

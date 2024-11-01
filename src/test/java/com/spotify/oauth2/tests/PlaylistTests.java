package com.spotify.oauth2.tests;

import static com.spotify.oauth2.api.applicationApi.PlaylistApi.get;
import static com.spotify.oauth2.api.applicationApi.PlaylistApi.post;
import static com.spotify.oauth2.api.applicationApi.PlaylistApi.update;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;

import io.qameta.allure.Description;
import io.restassured.response.Response;

public class PlaylistTests {
		
	@Test(description = "Should be able to create a Playlist")
	public void ShouldBeAbleToCreatePlaylist() {
		Playlist requestPlaylist= PlaylistBuilder("New Playlist","New playlist description",false);
		
		Response response= post(requestPlaylist);
		assertStatusCode(response.getStatusCode(),201);
		Playlist responsePlaylist = response.as(Playlist.class);
			
		assertPlaylistEqual(responsePlaylist, requestPlaylist);
	}
	
	@Description("This is the description of a test case of getting a playlist")
	@Test(description = "Should be able to get a Playlist")
	public void ShouldBeAbleToGetPlaylist() {
		Playlist requestPlaylist= PlaylistBuilder("Updated Playlist Name","Updated playlist description",true);
				
		Response response= get(DataLoader.getInstance().getGetPlaylistId());
		assertStatusCode(response.getStatusCode(), 200);		
		Playlist responsePlaylist= response.as(Playlist.class);		
		assertPlaylistEqual(responsePlaylist, requestPlaylist);
		
	}
	
	@Test(description = "Should be able to update a Playlist")
	public void ShouldBeAbleToUpdatePlaylist() {
		Playlist requestPlaylist= PlaylistBuilder("New Playlist","New playlist description",false);
		
		Response response= update(DataLoader.getInstance().getUpdatePlaylistId(),requestPlaylist);
		assertStatusCode(response.getStatusCode(), 200);
	}
	
	//Negative test cases
	@Test(description = "Should not be able to create a Playlist without Name")
	public void ShouldNotBeAbleToCreatePlaylistWithoutName() {
		
		Playlist requestPlaylist= PlaylistBuilder("","New playlist description",false);
		
		Response response = post(requestPlaylist);
		assertStatusCode(response.getStatusCode(), 400);		
		Error error= response.as(com.spotify.oauth2.pojo.Error.class);
		
		assertError(error,400,"Missing required field: name");		
	}

	@Test(description = "Should not be able to create a Playlist with exprired Token")
	public void ShouldNotBeAbleToCreatePlaylistWithExpiredToken() {
		String invalidToken = "dummyvalue12345";
		
		Playlist requestPlaylist= PlaylistBuilder("New Playlist","New playlist description",false);
		
		Response response= post(invalidToken,requestPlaylist);
		assertStatusCode(response.getStatusCode(), 401);
		
		Error error = response.as(Error.class);
		
		assertError(error, 401, "Invalid access token");
	}
	
	public Playlist PlaylistBuilder(String name, String description, boolean _public ) {
		Playlist requestPlaylist = Playlist.builder().
						name(name).
						description(description).
						_public(_public).
						build();
		
		
		/*		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName(name);
		requestPlaylist.setDescription(description);
		requestPlaylist.set_public(_public);*/
		
//		Playlist requestPlaylist = new Playlist().
//						setName(name).
//						setDescription(description).
//						setPublic(_public);		
		return requestPlaylist;
	}
	
	public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {
		assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.get_public(),equalTo(requestPlaylist.get_public()));
	}
	
	public void assertStatusCode(int actualStatusCode,int expectedStatusCode) {
		assertThat(actualStatusCode,equalTo(expectedStatusCode));
	}
	
	public void assertError(Error responseError, int expectedStatusCode,String expectedMsg) {
		assertThat(responseError.getError().getStatus(),equalTo(expectedStatusCode));
		assertThat(responseError.getError().getMessage(),equalTo(expectedMsg));
	}
}

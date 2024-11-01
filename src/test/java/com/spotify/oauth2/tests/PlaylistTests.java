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

import io.restassured.response.Response;

public class PlaylistTests {
	
	@Test
	public void ShouldBeAbleToCreatePlaylist() {
		
		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName("New Playlist");
		requestPlaylist.setDescription("New playlist description");
		requestPlaylist.setPublic(false);
		
		Response response= post(requestPlaylist);
		assertThat(response.getStatusCode(),equalTo(201));
		Playlist responsePlaylist = response.as(Playlist.class);
		
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
		
		Response response= get(DataLoader.getInstance().getGetPlaylistId());
		assertThat(response.getStatusCode(),equalTo(200));
		
		Playlist responsePlaylist= response.as(Playlist.class);

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
		
		Response response= update(DataLoader.getInstance().getUpdatePlaylistId(),requestPlaylist);
		assertThat(response.getStatusCode(),equalTo(200));

	}
	
	//Negative test cases
	@Test
	public void ShouldNotBeAbleToCreatePlaylistWithoutName() {
		
		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName("");
		requestPlaylist.setDescription("New playlist description");
		requestPlaylist.setPublic(false);
		
		Response response = post(requestPlaylist);
		assertThat(response.getStatusCode(),equalTo(400));
		
		Error error= response.as(com.spotify.oauth2.pojo.Error.class);
				
		assertThat(error.getError().getStatus(),equalTo(400));
		assertThat(error.getError().getMessage(),equalTo("Missing required field: name"));
	}

	@Test
	public void ShouldNotBeAbleToCreatePlaylistWithExpiredToken() {
		String invalidToken = "dummyvalue12345";
		
		Playlist requestPlaylist = new Playlist();
		requestPlaylist.setName("New Playlist");
		requestPlaylist.setDescription("New playlist description");
		requestPlaylist.setPublic(false);
		
		Response response= post(invalidToken,requestPlaylist);
		assertThat(response.getStatusCode(),equalTo(401));
		
		Error error = response.as(Error.class);
		
		assertThat(error.getError().getMessage(),equalTo("Invalid access token"));
	}
}

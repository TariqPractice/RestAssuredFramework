package com.spotify.oauth2.api.applicationApi;
import static com.spotify.oauth2.api.Route.*;
import com.spotify.oauth2.api.RestResource;

import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.pojo.Playlist;

import io.restassured.response.Response;

public class PlaylistApi {

//	static String accessToken="BQA6qYdvqZO6MnoOU23MLcIIvqmC8izeLwSvpvdkz9HZP3nkzb9ge9bGw9RI0tmEYVrtw0lL8RW2iPY_Zsu3CBD649D_MxF1FCc5Spxnn9jTD_n3tWCpjGVWPzOwmg7Df9JMUlSWjYTQXlXpGStXzNyei-MJLk5zPXNkdvcmNxMYJNKejqEXuvaczuPFlInb8jz-DPxpVGnLbOtx7nWH1nttErbB10WhxNUb9Ao1kxNq_Kv2vmMaRZ9lWHS0r4yifzNU1m5Y9xjDTsUk8BIr2Rdh";
	//static String accessToken= TokenManager.getToken();
	
	public static Response post(Playlist requestPlaylist) {
		return RestResource.post(USERS+"/31riagxrmqgrxbxp3og5lon5zvvm"+PLAYLISTS,TokenManager.getToken() , requestPlaylist);
	}
	
	public static Response post(String token,Playlist requestPlaylist) {
		
		return RestResource.post(USERS+"/31riagxrmqgrxbxp3og5lon5zvvm"+PLAYLISTS, token, requestPlaylist);
	}
	
	public static Response get(String playlistId) {
		
		return RestResource.get(PLAYLISTS+"/"+playlistId,TokenManager.getToken());
	}
	
	public static Response update(String playlistId,Playlist requestPlaylist) {
		return RestResource.update(PLAYLISTS+"/"+playlistId,TokenManager.getToken(), requestPlaylist);
	}
	

}

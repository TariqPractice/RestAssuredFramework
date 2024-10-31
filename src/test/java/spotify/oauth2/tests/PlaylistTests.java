package spotify.oauth2.tests;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PlaylistTests {
	
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	String accessToken="BQCR6pVQlsYIJg2QdtJL-up6tXvH74XnWWuVdVT3vTR6Iz7w55T1FWFNPLGEKUfkrJhiYkbqvkToo0c9tmZmQbsz5EQ-9EQZXAGXM1RuSQr9i-ut6stLdMuMZQY62ek7wnL9TSlQWjapbwZZiU2_Q8bl4q7IHyifa2waASN9aP2A4B9yABtz037GM7n8sEquYK8YM4BEVvOHQfud5lrQH3XxPDnUB6aD1UmHjl3HbZ7TPkrt0BzIb_TdhFhmH5R06kiD2fGp597FNRGw45r79hUj";
	
	@BeforeClass
	public void beforeClass() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
									setBaseUri("https://api.spotify.com").
									setBasePath("/v1").
									addHeader("Authorization", "Bearer "+accessToken).
									setContentType(ContentType.JSON).log(LogDetail.ALL);
		requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
									expectContentType(ContentType.JSON).
									log(LogDetail.ALL);
		responseSpecification = responseSpecBuilder.build();
	}

	@Test
	public void ShouldBeAbleToCreatePlaylist() {
		String payload ="{\r\n"
				+ "    \"name\": \"New Playlist\",\r\n"
				+ "    \"description\": \"New playlist description\",\r\n"
				+ "    \"public\": false\r\n"
				+ "}";
		
		given(requestSpecification).
				body(payload).
		when().post("/users/31riagxrmqgrxbxp3og5lon5zvvm/playlists").
		then().spec(responseSpecification).
				assertThat().
				statusCode(201).body("name", equalTo("New Playlist"),
						"description",equalTo("New playlist description"),
						"public", equalTo(false));
	}

	@Test
	public void ShouldBeAbleToGetPlaylist() {
		
		given(requestSpecification).
		when().get("/playlists/1WTK34AP0Wr9rNSytCE7iC").
		then().spec(responseSpecification).
				assertThat().
				statusCode(200).body("name", equalTo("Updated Playlist Name"),
						"description",equalTo("Updated playlist description"),
						"public", equalTo(true));
	}
}

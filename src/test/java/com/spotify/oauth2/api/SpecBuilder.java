package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.BaseURI;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

	public static RequestSpecification getRequestspec() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
//				setBaseUri(System.getProperty("BASE_URI")).//enable to run in maven
//				setBaseUri("https://api.spotify.com").
				setBaseUri(BaseURI.getBaseURI()).
				setBasePath(Route.BASE_PATH).
				setContentType(ContentType.JSON).
				addFilter(new AllureRestAssured()).
				log(LogDetail.ALL);

			return requestSpecBuilder.build();
	}
	
	public static RequestSpecification getAccountRequestspec() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
//				setBaseUri(System.getProperty("ACCOUNT_BASE_URI")).//enable to run in maven
//				setBaseUri("https://accounts.spotify.com").
				setBaseUri(BaseURI.getAccountBaseURI()).
				setContentType(ContentType.URLENC).
				addFilter(new AllureRestAssured()).
				log(LogDetail.ALL);

			return requestSpecBuilder.build();
	}

	public static ResponseSpecification getResponseSpec() {
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
				log(LogDetail.ALL);
		
		return responseSpecBuilder.build();

	}
}

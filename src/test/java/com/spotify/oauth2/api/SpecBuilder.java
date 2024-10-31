package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

	static String accessToken="BQBF9MHXEsTffJ6xu8aMlIAG1TACGIANKwkmLutW667_qae_p77qCkit-K4UEWXSie1ZjBanqprP6zWdcGPngXT2psFBntH9x5NUJg5FnxC8BjhOinKSvjEagYQe5uxwV1ezUYSuMSwk3Zquyz2WcidFGZF3NmfNT9sk5gMJOCtUCstYVWZgy9eA3P-qbHKCH6yhCZk-sojzmIjqDJrw9HrbjzipAYAAPPl6HuQktuD42hl-jUmXT_78zy3Dfbu37Si-LfQwHtPbhozyDMTvXwTU";

	public static RequestSpecification getRequestspec() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
				setBaseUri("https://api.spotify.com").
				setBasePath("/v1").
				addHeader("Authorization", "Bearer "+accessToken).
				setContentType(ContentType.JSON).log(LogDetail.ALL);

			return requestSpecBuilder.build();
	}

	public static ResponseSpecification getResponseSpec() {
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
				log(LogDetail.ALL);
		
		return responseSpecBuilder.build();

	}
}

package com.spotify.oauth2.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

	public static String generateName() {
		Faker faker = new Faker();
		String name=faker.regexify("[A-Za-z0-9 ,_-]{10}");//get from you development team to create a name
		
		return "Playlist "+name;
	}
	
	public static String generateDescription() {
		Faker faker = new Faker();
		String description=faker.regexify("[ A-Za-z0-9_@./#&+-]{50}");//get from you development team to create a name
		
		return "Description "+description;
	}
}

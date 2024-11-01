package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Value - ye define karne se hume apna varible private likhne ki bhi zarurat nahi
@Getter @Setter
@JacksonStdImpl //with this lombok will support Jackson annotation like JasonInclude
@NoArgsConstructor // Bina-argument constructor for deserialization
@AllArgsConstructor // All argument constructor for flexibility
@Builder
//@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

@JsonProperty("error")
private InnerError error;
}

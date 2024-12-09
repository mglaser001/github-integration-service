package com.branch_app.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GitHubUserResponse(@JsonProperty("user_name") String userName,
                                 @JsonProperty("display_name") String displayName,
                                 @JsonProperty("avatar") String avatar,
                                 @JsonProperty("geo_location") String geoLocation,
                                 @JsonProperty("email") String email,
                                 @JsonProperty("url") String url,
                                 @JsonProperty("created_at") String createdAt,
                                 @JsonProperty("repos") List<GitHubRepoResponse> repos){}

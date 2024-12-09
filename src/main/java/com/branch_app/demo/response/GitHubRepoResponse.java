package com.branch_app.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubRepoResponse(
        @JsonProperty("name") String name,
        @JsonProperty("url") String url) {}
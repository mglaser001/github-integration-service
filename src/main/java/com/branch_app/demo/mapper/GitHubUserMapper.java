package com.branch_app.demo.mapper;

import com.branch_app.demo.response.GitHubRepoResponse;
import com.branch_app.demo.response.GitHubUserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a mapper class for mapping GitHub jsons to response records
 *
 * @author Michael Glaser
 * @since 2024-12-08
 */
public class GitHubUserMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Maps userJson and reposJson strings into their response classes.
     *
     * @param (userJson, reposJson)
     * @return A ResponseEntity containing a GitHubUserDetailsRecord with user details and repos.
     */
    public static GitHubUserResponse mapToUserRecord(String userJson, String reposJson) throws JsonProcessingException {

        JsonNode rootNode = objectMapper.readTree(userJson);

        String userName = rootNode.get("login").asText();
        String displayName = rootNode.hasNonNull("name") ? rootNode.get("name").asText() : null;
        String avatar = rootNode.get("avatar_url").asText();
        String geoLocation = rootNode.hasNonNull("location") ? rootNode.get("location").asText() : null;
        String email = rootNode.hasNonNull("email") ? rootNode.get("email").asText() : null;
        String url = rootNode.get("html_url").asText();
        String createdAt = rootNode.get("created_at").asText();

        List<GitHubRepoResponse> repos = new ArrayList<>();
        List<JsonNode> repoNodes = objectMapper.readValue(reposJson, new TypeReference<List<JsonNode>>() {});
        for (JsonNode repoNode : repoNodes) {
            String repoName = repoNode.get("name").asText();
            String repoUrl = repoNode.get("html_url").asText();
            repos.add(new GitHubRepoResponse(repoName, repoUrl));
        }

        return new GitHubUserResponse(userName, displayName, avatar, geoLocation, email, url, createdAt, repos);
    }
}

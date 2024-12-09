package com.branch_app.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

/**
 * This class is responsible for retrieving user and repos data from the GitHub API.
 *
 * @author Michael Glaser
 * @since 2024-12-08
 */
@Component
public class GitHubClient {

    private static final Logger logger = LoggerFactory.getLogger(GitHubClient.class);
    private final RestClient restClient;

    public GitHubClient(@Value("${github.api.base-url}") String baseUrl, RestClient.Builder builder){
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public String getUser(String userName) {
        logger.debug("Fetching user details for username: {}", userName);

        try{
            String response = restClient.get()
                    .uri("/users/" + userName)
                    .retrieve()
                    .body(String.class);

            logger.info("Successfully fetched user details for username: {} from: ", response, "/users/" + userName);
            return response;
        }catch (HttpClientErrorException e){
            logger.error("ERROR fetched user details for username: {}", userName);
            throw e;
        }
    }

    public String getUserRepos(String userName) {
        logger.debug("Fetching user repos for username: {}", userName);
        try{
            String response = restClient.get()
                    .uri("/users/" + userName + "/repos")
                    .retrieve()
                    .body(String.class);
            logger.info("Successfully fetched user repos for username: {} from: ", response, "/users/" + userName  + "/repos");
            return response;
        }catch (HttpClientErrorException e){
            logger.error("ERROR fetched user repos for username: {}", userName);
            throw e;
        }
    }

}

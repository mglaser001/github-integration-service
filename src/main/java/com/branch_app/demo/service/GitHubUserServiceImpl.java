package com.branch_app.demo.service;

import com.branch_app.demo.client.GitHubClient;
import com.branch_app.demo.exception.ApiRequestException;
import com.branch_app.demo.mapper.GitHubUserMapper;
import com.branch_app.demo.response.GitHubUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * This class is a service for retrieving GitHub user details and their repos.
 *
 * @author Michael Glaser
 * @since 2024-12-08
 */
@Service
public class GitHubUserServiceImpl implements GitHubUserService{

    private static final Logger logger = LoggerFactory.getLogger(GitHubUserServiceImpl.class);
    private final GitHubClient client;

    public GitHubUserServiceImpl(GitHubClient client) {
        this.client = client;
    }

    /**
     * Fetches and aggregates a GitHub users details and their repositories.
     * Results are cached for further calls.
     *
     * @param userName
     * @return A GitHubUserDetailsRecord containing user details and list of their repositories.
     */
    @Cacheable(value = "userCache", key = "#userName")
    public GitHubUserResponse getUserDetails(String userName){
        CompletableFuture<String> userJsonFuture = CompletableFuture.supplyAsync(() -> client.getUser(userName));

        CompletableFuture<String> reposJsonFuture = CompletableFuture.supplyAsync(() -> client.getUserRepos(userName));

        return userJsonFuture.thenCombine(reposJsonFuture, this::mapToUserRecord)
                .exceptionally(e -> {
                    logger.error("Error fetching user details for username: {}", userName, e);
                    throw new ApiRequestException(e.getMessage());
                })
                .orTimeout(5, TimeUnit.SECONDS)
                .join();
    }

    private GitHubUserResponse mapToUserRecord(String userJson, String reposJson) {
        try {
            return GitHubUserMapper.mapToUserRecord(userJson, reposJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

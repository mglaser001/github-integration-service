package com.branch_app.demo.client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withRawStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(GitHubClient.class)
class GitHubClientTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private ObjectMapper mapper;

    private final String baseUrl = "https://api.github.com/";

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetUserSuccess() {
        String username = "octocat";
        String expectedResponse = "{\"name\": \"test\", \"id\": 1}";

        server.expect((requestTo(baseUrl + "users/" +username))).andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        String response = gitHubClient.getUser(username);

        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetUserHttpClientErrorException() {
        String username = "unknownUser";
        String expectedResponse = "{\"name\": \"test\", \"id\": 1}";

        server.expect((requestTo(baseUrl + "users/" +username))).andRespond(withRawStatus(404));

        assertThrows(HttpClientErrorException.class, () -> gitHubClient.getUser(username));
    }

    @Test
    void testGetUserReposSuccess() {
        String username = "test";
        String expectedResponse = "{\"name\": \"test\", \"id\": 1}";

        server.expect((requestTo(baseUrl + "users/" + username + "/repos"))).andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        String response = gitHubClient.getUserRepos(username);

        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetUserReposHttpClientErrorException() {
        String username = "unknownUser";
        String expectedResponse = "{\"name\": \"test\", \"id\": 1}";

        server.expect((requestTo(baseUrl + "users/" + username + "/repos"))).andRespond(withRawStatus(404));

        assertThrows(HttpClientErrorException.class, () -> gitHubClient.getUserRepos(username));
    }
}
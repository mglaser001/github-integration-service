package com.branch_app.demo.service;

import com.branch_app.demo.client.GitHubClient;
import com.branch_app.demo.response.GitHubUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GitHubUserServiceImplTest {

    @Mock
    private GitHubClient client;

    @InjectMocks
    private GitHubUserServiceImpl gitHubUserService;

    private String testUserName;
    private String userJson;
    private String reposJson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUserName = "test";
        userJson = "{"
                + "\"login\": \"test\","
                + "\"name\": \"test test Name\","
                + "\"avatar_url\": \"test.avatar\","
                + "\"location\": \"LA\","
                + "\"email\": \"test@test.com\","
                + "\"html_url\": \"testoctocat\","
                + "\"created_at\": \"1111-01-01\""
                + "}";

        reposJson = "[{ \"name\": \"repo1\", \"html_url\": \"github.com/repo1\" },"
                + "{ \"name\": \"repo2\", \"html_url\": \"github.com/repo2\" }]";
    }

    @Test
    void testGetUserDetails_Success() {
        // Mocking client calls
        when(client.getUser(testUserName)).thenReturn(userJson);
        when(client.getUserRepos(testUserName)).thenReturn(reposJson);

        GitHubUserResponse result = gitHubUserService.getUserDetails(testUserName);

        assertEquals("test", result.userName());
        assertEquals("test test Name", result.displayName());
        assertEquals("test.avatar", result.avatar());
        assertEquals("LA", result.geoLocation());
        assertEquals("1111-01-01", result.createdAt());

        assertNotNull(result.repos());
        assertEquals(2, result.repos().size());
        assertEquals("repo1", result.repos().get(0).name());
        assertEquals("github.com/repo2", result.repos().get(1).url());

        verify(client, times(1)).getUser(testUserName);
        verify(client, times(1)).getUserRepos(testUserName);
    }

    @Test
    void testGetUserDetails_ClientError() {
        when(client.getUser(testUserName)).thenThrow(new RuntimeException("User not found"));
        when(client.getUserRepos(testUserName)).thenThrow(new RuntimeException("Repos not found"));

        Exception exception = assertThrows(CompletionException.class, () -> {
            gitHubUserService.getUserDetails(testUserName);
        });

        assertTrue(exception.getMessage().contains("User not found"));

        verify(client, times(1)).getUser(testUserName);
        verify(client, times(1)).getUserRepos(testUserName);
    }

    @Test
    void testGetUserDetails_Timeout() {
        when(client.getUser(testUserName)).thenAnswer(invocation -> {
            Thread.sleep(5500);
            return userJson;
        });
        when(client.getUserRepos(testUserName)).thenReturn(reposJson);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            gitHubUserService.getUserDetails(testUserName);
        });

        assertTrue(exception.getMessage().contains("Timeout"));

        verify(client, times(1)).getUser(testUserName);
        verify(client, times(1)).getUserRepos(testUserName);
    }
}
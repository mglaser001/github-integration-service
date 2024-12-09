package com.branch_app.demo.controller;

import com.branch_app.demo.response.GitHubRepoResponse;
import com.branch_app.demo.response.GitHubUserResponse;
import com.branch_app.demo.service.GitHubUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GitHubUserControllerTest {

    @Mock
    private GitHubUserServiceImpl gitHubService;

    @InjectMocks
    private GitHubUserController gitHubUserController;

    private String userName = "octocat";
    private GitHubUserResponse testUserDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUserDetails = new GitHubUserResponse(
                "test",
                "test Display Name",
                "test.avatar",
                "LA",
                "test@test.com",
                "testGit.com",
                "1111-01-01",
                List.of(new GitHubRepoResponse("repo1", "github.com/repo1"), new GitHubRepoResponse("repo2", "github.com/repo2"))
        );

    }

    @Test
    void testGetUserData_Success() throws Exception {
        when(gitHubService.getUserDetails(userName)).thenReturn(testUserDetails);

        ResponseEntity<GitHubUserResponse> response = gitHubUserController.getUserData(userName);

        assertEquals("test", response.getBody().userName());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("test.avatar", response.getBody().avatar());

        assertEquals(2, response.getBody().repos().size());

        assertEquals("repo1", response.getBody().repos().get(0).name());
        assertEquals("github.com/repo1", response.getBody().repos().get(0).url());
        assertEquals("repo2", response.getBody().repos().get(1).name());

        verify(gitHubService, times(1)).getUserDetails(userName);
    }
}
package com.branch_app.demo.controller;

import com.branch_app.demo.response.GitHubUserResponse;
import com.branch_app.demo.service.GitHubUserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class is a REST controller for handling GitHub user-related requests.
 *
 * @author Michael Glaser
 * @since 2024-12-08
 */
@RestController
@RequestMapping("/api/v1/github/users")
public class GitHubUserController {

    private final GitHubUserServiceImpl gitHubService;

    public GitHubUserController(GitHubUserServiceImpl gitHubService){
        this.gitHubService = gitHubService;
    }

    /**
     * GET GitHub user details and their repos.
     *
     * @param userName The GitHub username provided as a path variable.
     * @return A ResponseEntity containing a GitHubUserDetailsRecord with user details and repos.
     */
    @GetMapping("/{userName}")
    public ResponseEntity<GitHubUserResponse> getUserData(@PathVariable String userName){
        GitHubUserResponse response = gitHubService.getUserDetails(userName);
        return ResponseEntity.ok(response);
    }

}

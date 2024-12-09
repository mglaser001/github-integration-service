package com.branch_app.demo.service;

import com.branch_app.demo.response.GitHubUserResponse;

public interface GitHubUserService {
    public GitHubUserResponse getUserDetails(String username);
}

package com.branch_app.demo.mapper;

import com.branch_app.demo.response.GitHubUserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitHubUserMapperTest {

    private String userJson;
    private String reposJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
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
    void testMapToUserRecord() throws JsonProcessingException {
        GitHubUserResponse userDetailsRecord = GitHubUserMapper.mapToUserRecord(userJson, reposJson);

        assertEquals("test", userDetailsRecord.userName());
        assertEquals("test test Name", userDetailsRecord.displayName());
        assertEquals("test.avatar", userDetailsRecord.avatar());
        assertEquals("LA", userDetailsRecord.geoLocation());
        assertEquals("test@test.com", userDetailsRecord.email());
        assertEquals("testoctocat", userDetailsRecord.url());
        assertEquals("1111-01-01", userDetailsRecord.createdAt());

        assertEquals(2, userDetailsRecord.repos().size());

        assertEquals("repo1", userDetailsRecord.repos().get(0).name());
        assertEquals("github.com/repo1", userDetailsRecord.repos().get(0).url());

        assertEquals("repo2", userDetailsRecord.repos().get(1).name());
        assertEquals("github.com/repo2", userDetailsRecord.repos().get(1).url());
    }

}
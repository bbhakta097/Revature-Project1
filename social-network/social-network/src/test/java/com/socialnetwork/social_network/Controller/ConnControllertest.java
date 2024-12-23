package com.socialnetwork.social_network.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConnControllertest {

    private static HttpClient httpClient;
    private static final String BASE_URL = "http://localhost:8080/connections";

    @BeforeAll
    public static void setup() {
        httpClient = HttpClient.newHttpClient();
    }

    @Test
    public void followUser_ShouldReturn200() throws IOException, InterruptedException {
        Long userId = 1L;
        Long targetUserId = 2L;

        String json = String.valueOf(targetUserId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + userId + "/follow"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(), "Expected status 200, got: " + response.statusCode());
    }

    @Test
    public void getFollowers_ShouldReturn200() throws IOException, InterruptedException {
        Long userId = 1L;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + userId + "/followers"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(), "Expected status 200, got: " + response.statusCode());
    }

    @Test
    public void getFollowing_ShouldReturn200() throws IOException, InterruptedException {
        Long userId = 1L;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + userId + "/following"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(), "Expected status 200, got: " + response.statusCode());
    }

    @Test
    public void unfollowUser_ShouldReturn200() throws IOException, InterruptedException {
        Long userId = 1L;
        Long targetUserId = 2L;

        String json = String.valueOf(targetUserId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + userId + "/unfollow"))
                .header("Content-Type", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(), "Expected status 200, got: " + response.statusCode());
    }
}

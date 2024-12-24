package com.socialnetwork.social_network.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PostControllerTest {

    private static HttpClient httpClient;

    @BeforeAll
    public static void setup() {
        httpClient = HttpClient.newHttpClient();
    }

    @Test
    public void createPost_ShouldReturn200() throws IOException, InterruptedException {

        String json = "\"This is a test post\"";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/posts/1"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(),
                "Expected status 200, got: " + response.statusCode() + ". Response Body: " + response.body());
        System.out.println("Response: " + response.body());
    }

    @Test
    public void getPostsByUser_ShouldReturn200() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/posts/1"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(),
                "Expected status 200, got: " + response.statusCode() + ". Response Body: " + response.body());
        System.out.println("Response: " + response.body());
    }
}

package com.socialnetwork.social_network.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthControllerTest {

    private static HttpClient httpClient;

    @BeforeAll
    public static void setup() {
        httpClient = HttpClient.newHttpClient();
    }

    @Test
    public void registerUser_ShouldReturn200() throws IOException, InterruptedException {
        String json = "{"
                + "\"username\":\"testersuser\","
                + "\"email\":\"testerusers@example.com\","
                + "\"password\":\"password123\""
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/auth/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(),
                "Expected status 200, got: " + response.statusCode() + ". Response Body: " + response.body());
    }

    @Test
    public void loginUser_ShouldReturn200() throws IOException, InterruptedException {
        String json = """
                {
                    "username": "newuser",
                    "password": "password123"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(), "Expected status 200, got: " + response.statusCode());
    }

    @Test
    public void getUserProfile_ShouldReturn200() throws IOException, InterruptedException {
        String username = "newuser";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/auth/users/" + username))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(), "Expected status 200, got: " + response.statusCode());
    }

    @Test
    public void updateUserProfile_ShouldReturn200() throws IOException, InterruptedException {
        Long userId = 1L;

        String json = """
                {
                    "username": "updateduser",
                    "email": "updated@example.com",
                    "bio": "Updated bio"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/auth/users/" + userId + "/update"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(), "Expected status 200, got: " + response.statusCode());
    }

    @Test
    public void getAllUsers_ShouldReturn200() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/auth/users"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode(), "Expected status 200, got: " + response.statusCode());
    }
}

package com.socialnetwork.social_network.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SearchControllerTest {

        private final HttpClient httpClient = HttpClient.newHttpClient();

        @Test
        public void searchUsers_ValidQuery_ShouldReturn200() throws IOException, InterruptedException {

                String query = "phatzzo";

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/search/users?query=" + query))
                                .header("Accept", "application/json")
                                .GET()
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                assertEquals(200, response.statusCode(),
                                "Expected status 200 for valid user search, but got: " + response.statusCode());
                System.out.println("Response for user search: " + response.body());
        }

        @Test
        public void searchUsers_EmptyQuery_ShouldReturn400() throws IOException, InterruptedException {

                String query = "";

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/search/users?query=" + query))
                                .header("Accept", "application/json")
                                .GET()
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                assertEquals(400, response.statusCode(),
                                "Expected status 400 for empty user search query, but got: " + response.statusCode());
                System.out.println("Response for empty user query: " + response.body());
        }

        @Test
        public void searchPosts_ValidQuery_ShouldReturn200() throws IOException, InterruptedException {

                String query = "test";

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/search/posts?query=" + query))
                                .header("Accept", "application/json")
                                .GET()
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                assertEquals(200, response.statusCode(),
                                "Expected status 200 for valid post search, but got: " + response.statusCode());
                System.out.println("Response for post search: " + response.body());
        }

        @Test
        public void searchPosts_EmptyQuery_ShouldReturn400() throws IOException, InterruptedException {

                String query = "";

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/search/posts?query=" + query))
                                .header("Accept", "application/json")
                                .GET()
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                assertEquals(400, response.statusCode(),
                                "Expected status 400 for empty post search query, but got: " + response.statusCode());
                System.out.println("Response for empty post query: " + response.body());
        }
}

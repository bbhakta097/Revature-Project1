package com.socialnetwork.social_network.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CommentControllerTest {

        private final HttpClient httpClient = HttpClient.newHttpClient();

        @Test
        public void addComment_ValidContent_ShouldReturn200() throws IOException, InterruptedException {

                String content = "This is a valid comment";
                Long postId = 1L;
                Long userId = 2L;

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/comments/" + postId + "?userId=" + userId))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(content))
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                assertEquals(200, response.statusCode(),
                                "Expected status 200 for valid comment, but got: " + response.statusCode());
        }

        @Test
        public void addComment_EmptyContent_ShouldReturn400() throws IOException, InterruptedException {

                String emptyContent = "";
                Long postId = 1L;
                Long userId = 2L;

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/comments/" + postId + "?userId=" + userId))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(emptyContent))
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                assertEquals(400, response.statusCode(),
                                "Expected status 400 for empty comment, but got: " + response.statusCode());
                System.out.println("Response for empty content: " + response.body());
        }

        @Test
        public void addComment_NullContent_ShouldReturn400() throws IOException, InterruptedException {

                String nullContent = "";
                Long postId = 1L;
                Long userId = 2L;

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/comments/" + postId + "?userId=" + userId))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(nullContent))
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                assertEquals(400, response.statusCode(),
                                "Expected status 400 for null content, but got: " + response.statusCode());
                System.out.println("Response for null content: " + response.body());
        }
}

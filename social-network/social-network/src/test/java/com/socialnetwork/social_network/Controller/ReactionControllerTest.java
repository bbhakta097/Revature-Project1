package com.socialnetwork.social_network.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ReactionControllerTest {

        private final HttpClient httpClient = HttpClient.newHttpClient();

        @Test
        public void likePost_ValidRequest_ShouldReturn200() throws IOException, InterruptedException {

                Long postId = 1L;
                Long userId = 2L;

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/reactions/" + postId + "/like?userId=" + userId))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.noBody())
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                assertEquals(200, response.statusCode(),
                                "Expected status 200 for liking a post, but got: " + response.statusCode());
                System.out.println("Response for liking post: " + response.body());
        }

        @Test
        public void getLikeCount_ValidPostId_ShouldReturn200() throws IOException, InterruptedException {

                Long postId = 1L;

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8080/reactions/" + postId + "/likes"))
                                .header("Accept", "application/json")
                                .GET()
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                assertEquals(200, response.statusCode(),
                                "Expected status 200 for retrieving like count, but got: " + response.statusCode());
                System.out.println("Like count response: " + response.body());
        }

}

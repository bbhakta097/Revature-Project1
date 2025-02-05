package com.socialnetwork.social_network.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialnetwork.social_network.DTO.UserConnDto;
import com.socialnetwork.social_network.Service.ConnService;

@RestController
@RequestMapping("/connections")
public class ConnController {

    private final ConnService connectionService;

    public ConnController(ConnService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping("/{userId}/follow")
    public ResponseEntity<String> followUser(
            @PathVariable Long userId,
            @RequestBody Long targetUserId) {
        String message = connectionService.followUser(userId, targetUserId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<Long> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.getFollowers(userId));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserConnDto>> getFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.getFollowing(userId));
    }

    @DeleteMapping("/{userId}/unfollow")
    public ResponseEntity<String> unfollowUser(
            @PathVariable Long userId,
            @RequestBody Long targetUserId) {
        String message = connectionService.unfollowUser(userId, targetUserId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{userId1}/ifFriends/{userId2}")
    public ResponseEntity<Boolean> checkIfFriends(@PathVariable Long userId1, @PathVariable Long userId2) {
        boolean areFriends = connectionService.ifFriends(userId1, userId2);
        return ResponseEntity.ok(areFriends);
    }

    @GetMapping("/{userId}/ifFollows/{userId2}")
    public ResponseEntity<Boolean> checkIfFollowing(@PathVariable Long userId, @PathVariable Long userId2) {
        boolean doesFollow = connectionService.ifFollows(userId, userId2);
        return ResponseEntity.ok(doesFollow);
    }

}

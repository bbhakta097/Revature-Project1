package com.socialnetwork.social_network.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.socialnetwork.social_network.DTO.UserConnDto;
import com.socialnetwork.social_network.Model.Connection;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.ConnRepo;
import com.socialnetwork.social_network.Repository.UserRepository;

@Service
public class ConnService {

    private final ConnRepo connectionRepository;
    private final UserRepository userRepository;

    public ConnService(ConnRepo connectionRepository, UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
    }

    /*
     * public String followUser(Long userId, Long targetUserId) {
     * userRepository.findById(userId).orElseThrow(() -> new
     * RuntimeException("User not found"));
     * userRepository.findById(targetUserId).orElseThrow(() -> new
     * RuntimeException("Target user not found"));
     * 
     * if (connectionRepository.findByUserIdAndConnectedUserId(userId,
     * targetUserId).isPresent()) {
     * return "You are already following this user.";
     * }
     * 
     * Connection connection = new Connection();
     * connection.setUserId(userId);
     * connection.setConnectedUserId(targetUserId);
     * connection.setStatus(Connection.Status.FOLLOWING);
     * connectionRepository.save(connection);
     * 
     * return "You are now following the user.";
     * }
     */

    public List<UserConnDto> getFollowers(Long userId) {
        List<Connection> connections = connectionRepository.findAllByConnectedUserId(userId);

        return connections.stream()
                .map(connection -> {
                    User user = userRepository.findById(connection.getUserId())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    return new UserConnDto(user.getId(), user.getUsername());
                })
                .collect(Collectors.toList());
    }

    public List<UserConnDto> getFollowing(Long userId) {
        List<Connection> connections = connectionRepository.findAllByUserId(userId);

        return connections.stream()
                .map(connection -> {
                    User user = userRepository.findById(connection.getConnectedUserId())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    return new UserConnDto(user.getId(), user.getUsername());
                })
                .collect(Collectors.toList());
    }

    public String unfollowUser(Long userId, Long targetUserId) {
        Connection connection = connectionRepository.findByUserIdAndConnectedUserId(userId, targetUserId)
                .orElseThrow(() -> new RuntimeException("You are not following this user."));

        connectionRepository.delete(connection);

        // Check if the reverse connection exists
        Optional<Connection> reverseConnection = connectionRepository.findByUserIdAndConnectedUserId(targetUserId,
                userId);

        if (reverseConnection.isPresent()) {
            reverseConnection.get().setStatus(Connection.Status.FOLLOWING);
            connectionRepository.save(reverseConnection.get());
        }

        return "You have unfollowed the user.";
    }

    public String followUser(Long userId, Long targetUserId) {
        // Ensure both users exist
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.findById(targetUserId).orElseThrow(() -> new RuntimeException("Target user not found"));

        // Check if a connection already exists
        Optional<Connection> existingConnection = connectionRepository.findByUserIdAndConnectedUserId(userId,
                targetUserId);

        if (existingConnection.isPresent()) {
            return "You are already following this user.";
        }

        // Create the following connection
        Connection connection = new Connection();
        connection.setUserId(userId);
        connection.setConnectedUserId(targetUserId);
        connection.setStatus(Connection.Status.FOLLOWING);
        connectionRepository.save(connection);

        // Check if the target user already follows the current user
        Optional<Connection> reverseConnection = connectionRepository.findByUserIdAndConnectedUserId(targetUserId,
                userId);

        if (reverseConnection.isPresent()) {
            // Update both connections to FRIENDS
            connection.setStatus(Connection.Status.FRIENDS);
            reverseConnection.get().setStatus(Connection.Status.FRIENDS);
            connectionRepository.save(connection);
            connectionRepository.save(reverseConnection.get());

            return "You are now friends with the user.";
        }

        return "You are now following the user.";
    }

    public boolean ifFriends(Long userId1, Long userId2) {
        // Check if userId1 follows userId2
        boolean user1FollowsUser2 = connectionRepository.existsByUserIdAndConnectedUserId(userId1, userId2);

        // Check if userId2 follows userId1
        boolean user2FollowsUser1 = connectionRepository.existsByUserIdAndConnectedUserId(userId2, userId1);

        // Return true if both follow each other
        return user1FollowsUser2 && user2FollowsUser1;
    }

}

package com.socialnetwork.social_network.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialnetwork.social_network.Model.Connection;

public interface ConnRepo extends JpaRepository<Connection, Long> {

    List<Connection> findAllByUserId(Long userId);

    List<Connection> findAllByConnectedUserId(Long connectedUserId);

    Optional<Connection> findByUserIdAndConnectedUserId(Long userId, Long connectedUserId);

    boolean existsByUserIdAndConnectedUserId(Long userId, Long connectedUserId);


}

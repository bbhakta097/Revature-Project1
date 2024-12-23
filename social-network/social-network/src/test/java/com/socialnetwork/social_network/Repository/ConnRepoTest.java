package com.socialnetwork.social_network.Repository;

import com.socialnetwork.social_network.Model.Connection;
import com.socialnetwork.social_network.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ConnRepoTest {

    @Autowired
    private ConnRepo connRepo;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindAllByUserId() {
        // Arrange
        User user1 = userRepository.save(new User("user1", "email1@example.com", "password"));
        User user2 = userRepository.save(new User("user2", "email2@example.com", "password"));
        connRepo.save(new Connection(user1.getId(), user2.getId(), Connection.Status.FOLLOWING));

        // Act
        List<Connection> connections = connRepo.findAllByUserId(user1.getId());

        // Assert
        assertThat(connections).hasSize(1);
        assertThat(connections.get(0).getConnectedUserId()).isEqualTo(user2.getId());
    }
}

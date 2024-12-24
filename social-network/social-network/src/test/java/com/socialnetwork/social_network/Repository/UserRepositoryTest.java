package com.socialnetwork.social_network.Repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.socialnetwork.social_network.Model.User;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {

        User user = new User("testuser", "test@example.com", "password");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("testuser");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testExistsByEmail() {

        userRepository.save(new User("user1", "test@example.com", "password"));

        boolean exists = userRepository.existsByEmail("test@example.com");

        assertThat(exists).isTrue();
    }
}

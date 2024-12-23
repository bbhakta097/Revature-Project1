package com.socialnetwork.social_network.Repository;

import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.Reaction;
import com.socialnetwork.social_network.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReactionRepositoryTest {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCountByPostId() {
        // Arrange
        User user = userRepository.save(new User("username", "email@example.com", "password"));
        Post post = postRepository.save(new Post("Test Post", user));
        reactionRepository.save(new Reaction(post, user, Reaction.Type.LIKE));

        // Act
        long likeCount = reactionRepository.countByPostId(post.getId());

        // Assert
        assertThat(likeCount).isEqualTo(1);
    }
}

package com.socialnetwork.social_network.Repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.User;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindAllByUserId() {

        User user = userRepository.save(new User("user1", "email@example.com", "password"));
        postRepository.save(new Post("Test post 1", user));
        postRepository.save(new Post("Test post 2", user));

        List<Post> posts = postRepository.findAllByUserId(user.getId());

        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getContent()).isEqualTo("Test post 1");
    }
}

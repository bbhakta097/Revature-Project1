package com.socialnetwork.social_network.Repository;

import com.socialnetwork.social_network.Model.Comment;
import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindAllByPostId() {
        // Arrange
        User user = userRepository.save(new User("username", "email@example.com", "password"));
        Post post = postRepository.save(new Post("Test post content", user));
        commentRepository.save(new Comment("First comment", user, post));

        // Act
        List<Comment> comments = commentRepository.findAllByPostId(post.getId());

        // Assert
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getContent()).isEqualTo("First comment");
    }
}

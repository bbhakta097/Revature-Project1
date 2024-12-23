package com.socialnetwork.social_network.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.Reaction;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.PostRepository;
import com.socialnetwork.social_network.Repository.ReactionRepository;
import com.socialnetwork.social_network.Repository.UserRepository;

class ReactionServiceTest {

    @Mock
    private ReactionRepository reactionRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReactionService reactionService;

    private Post mockPost;
    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockPost = new Post();
        mockPost.setId(1L);
        mockPost.setContent("Sample post content");

        mockUser = new User();
        mockUser.setId(2L);
        mockUser.setUsername("testUser");
    }

    @Test
    void likePost_Success() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mockUser));
        when(reactionRepository.findByPostIdAndUserId(1L, 2L)).thenReturn(Optional.empty());

        String result = reactionService.likePost(1L, 2L);

        assertEquals("Post liked successfully!", result);
        verify(reactionRepository, times(1)).save(any(Reaction.class));
    }

    @Test
    void likePost_PostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reactionService.likePost(1L, 2L);
        });

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void likePost_UserNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reactionService.likePost(1L, 2L);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void likePost_AlreadyLiked() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mockUser));
        when(reactionRepository.findByPostIdAndUserId(1L, 2L)).thenReturn(Optional.of(new Reaction()));

        String result = reactionService.likePost(1L, 2L);

        assertEquals("You already liked this post!", result);
        verify(reactionRepository, times(0)).save(any(Reaction.class));
    }

    @Test
    void getLikeCount_Success() {
        when(reactionRepository.countByPostId(1L)).thenReturn(5L);

        long count = reactionService.getLikeCount(1L);

        assertEquals(5L, count);
        verify(reactionRepository, times(1)).countByPostId(1L);
    }
}

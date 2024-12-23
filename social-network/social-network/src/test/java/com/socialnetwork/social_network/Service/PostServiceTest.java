package com.socialnetwork.social_network.Service;

import java.util.Arrays;
import java.util.List;
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

import com.socialnetwork.social_network.DTO.PostSearchDto;
import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.PostRepository;
import com.socialnetwork.social_network.Repository.UserRepository;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private User mockUser;
    private Post mockPost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");

        mockPost = new Post();
        mockPost.setId(1L);
        mockPost.setContent("Sample post content");
        mockPost.setUser(mockUser);
    }

    @Test
    void createPost_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        String result = postService.createPost(1L, "Sample post content");

        assertEquals("Post created successfully!", result);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void createPost_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postService.createPost(1L, "Sample post content");
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getPostsByUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(postRepository.findAllByUserId(1L)).thenReturn(Arrays.asList(mockPost));

        List<PostSearchDto> result = postService.getPostsByUser(1L);

        assertEquals(1, result.size());
        assertEquals("Sample post content", result.get(0).getContent());
        verify(postRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void getPostsByUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postService.getPostsByUser(1L);
        });

        assertEquals("User not found", exception.getMessage());
    }
}

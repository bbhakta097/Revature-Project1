package com.socialnetwork.social_network.Service;

import com.socialnetwork.social_network.DTO.PostSearchDto;
import com.socialnetwork.social_network.DTO.UserSearchDto;
import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.PostRepository;
import com.socialnetwork.social_network.Repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class SearchServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private SearchService searchService;

    private User mockUser;
    private Post mockPost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");

        mockPost = new Post();
        mockPost.setId(2L);
        mockPost.setContent("Test post content");
        mockPost.setUser(mockUser);
    }

    @Test
    void searchUsers_Success() {
        when(userRepository.searchByUsername("test")).thenReturn(Arrays.asList(mockUser));

        List<UserSearchDto> result = searchService.searchUsers("test");

        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
        verify(userRepository, times(1)).searchByUsername("test");
    }

    @Test
    void searchUsers_NoResults() {
        when(userRepository.searchByUsername("unknown")).thenReturn(Arrays.asList());

        List<UserSearchDto> result = searchService.searchUsers("unknown");

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).searchByUsername("unknown");
    }

    @Test
    void searchPosts_Success() {
        when(postRepository.searchByContent("Test")).thenReturn(Arrays.asList(mockPost));

        List<PostSearchDto> result = searchService.searchPosts("Test");

        assertEquals(1, result.size());
        assertEquals("Test post content", result.get(0).getContent());
        assertEquals("testUser", result.get(0).getUser().getUsername());
        verify(postRepository, times(1)).searchByContent("Test");
    }

    @Test
    void searchPosts_NoResults() {
        when(postRepository.searchByContent("unknown")).thenReturn(Arrays.asList());

        List<PostSearchDto> result = searchService.searchPosts("unknown");

        assertTrue(result.isEmpty());
        verify(postRepository, times(1)).searchByContent("unknown");
    }
}

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

import com.socialnetwork.social_network.Model.Comment;
import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.CommentRepository;
import com.socialnetwork.social_network.Repository.PostRepository;
import com.socialnetwork.social_network.Repository.UserRepository;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    private User mockUser;
    private Post mockPost;
    private Comment mockComment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");

        mockPost = new Post();
        mockPost.setId(2L);
        mockPost.setContent("Sample post content");

        mockComment = new Comment();
        mockComment.setId(3L);
        mockComment.setContent("Sample comment");
        mockComment.setUser(mockUser);
        mockComment.setPost(mockPost);
    }

    @Test
    void createComment_Success() {
        when(postRepository.findById(2L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);

        String result = commentService.createComment(2L, 1L, "Sample comment");

        assertEquals("Comment added successfully!", result);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void createComment_PostNotFound() {
        when(postRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            commentService.createComment(2L, 1L, "Sample comment");
        });

        assertEquals("Post not found", exception.getMessage());
    }



    @Test
    void getCommentsByPost_PostNotFound() {
        when(postRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            commentService.getCommentsByPost(2L);
        });

        assertEquals("Post not found", exception.getMessage());
    }
}

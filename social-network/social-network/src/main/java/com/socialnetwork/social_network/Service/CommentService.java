package com.socialnetwork.social_network.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.socialnetwork.social_network.DTO.CommentDto;
import com.socialnetwork.social_network.DTO.PostDto;
import com.socialnetwork.social_network.DTO.UserConnDto;
import com.socialnetwork.social_network.Model.Comment;
import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.CommentRepository;
import com.socialnetwork.social_network.Repository.PostRepository;
import com.socialnetwork.social_network.Repository.UserRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository,
            UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public String createComment(Long postId, Long userId, String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Comment content cannot be empty");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);

        commentRepository.save(comment);
        return "Comment added successfully!";
    }

    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User postOwner = post.getUser();
        UserConnDto postOwnerDto = new UserConnDto(postOwner.getId(), postOwner.getUsername());
        PostDto postDto = new PostDto(post.getId(), post.getContent(), post.getCreatedAt(), postOwnerDto);

        return commentRepository.findAllByPostId(postId).stream()
                .map(comment -> {
                    User commenter = comment.getUser();
                    UserConnDto commenterDto = new UserConnDto(commenter.getId(), commenter.getUsername());
                    return new CommentDto(
                            comment.getId(),
                            comment.getContent(),
                            comment.getCreatedAt(),
                            commenterDto,
                            postDto);
                })
                .collect(Collectors.toList());
    }

    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found with ID: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}

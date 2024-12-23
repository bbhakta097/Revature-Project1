package com.socialnetwork.social_network.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.socialnetwork.social_network.DTO.PostSearchDto;
import com.socialnetwork.social_network.DTO.UserConnDto;
import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.PostRepository;
import com.socialnetwork.social_network.Repository.ReactionRepository;
import com.socialnetwork.social_network.Repository.UserRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, ReactionRepository reactionRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.reactionRepository = reactionRepository;
    }

    public String createPost(Long userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUser(user);
        post.setContent(content.trim());

        postRepository.save(post);
        return "Post created successfully!";
    }

    public List<PostSearchDto> getPostsByUser(Long userId) {
        // Verify the user exists
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch posts and map to DTOs
        return postRepository.findAllByUserId(userId).stream()
                .map(post -> {
                    User user = post.getUser();
                    UserConnDto userDto = new UserConnDto(user.getId(), user.getUsername());
                    return new PostSearchDto(post.getId(), post.getContent(), post.getCreatedAt(), userDto, reactionRepository.countByPostId(post.getId()));
                })
                .collect(Collectors.toList());
    }
}

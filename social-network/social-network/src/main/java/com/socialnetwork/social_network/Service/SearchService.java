package com.socialnetwork.social_network.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.socialnetwork.social_network.DTO.PostSearchDto;
import com.socialnetwork.social_network.DTO.UserConnDto;
import com.socialnetwork.social_network.DTO.UserSearchDto;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.PostRepository;
import com.socialnetwork.social_network.Repository.ReactionRepository;
import com.socialnetwork.social_network.Repository.UserRepository;

@Service
public class SearchService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public SearchService(UserRepository userRepository, PostRepository postRepository, ReactionRepository reactionRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.reactionRepository = reactionRepository;
    }

    public List<UserSearchDto> searchUsers(String query) {
        return userRepository.searchByUsername(query).stream()
                .map(user -> new UserSearchDto(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public List<PostSearchDto> searchPosts(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query parameter cannot be empty.");
        }
        return postRepository.searchByContent(query).stream()
                .map(post -> {
                    User user = post.getUser();
                    UserConnDto userDto = new UserConnDto(user.getId(), user.getUsername());
                    //long likeCount = reactionRepository.countByPostIdAndReactionType(post.getId(), "LIKE");
                    System.out.println("Post ID: " + post.getId() + ", Likes: " + reactionRepository.countByPostId(post.getId()));
                    return new PostSearchDto(post.getId(), post.getContent(), post.getCreatedAt(), userDto, reactionRepository.countByPostId(post.getId()));
                })
                .collect(Collectors.toList());
    }
}

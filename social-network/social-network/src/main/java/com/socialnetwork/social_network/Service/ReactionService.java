package com.socialnetwork.social_network.Service;

import org.springframework.stereotype.Service;

import com.socialnetwork.social_network.Model.Post;
import com.socialnetwork.social_network.Model.Reaction;
import com.socialnetwork.social_network.Model.ReactionType;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.PostRepository;
import com.socialnetwork.social_network.Repository.ReactionRepository;
import com.socialnetwork.social_network.Repository.UserRepository;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ReactionService(ReactionRepository reactionRepository, PostRepository postRepository,
            UserRepository userRepository) {
        this.reactionRepository = reactionRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public String likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user has already liked the post
        if (reactionRepository.findByPostIdAndUserId(postId, userId).isPresent()) {
            return "You already liked this post!";
        }

        Reaction reaction = new Reaction();
        reaction.setPost(post);
        reaction.setUser(user);
        reaction.setReactionType(ReactionType.LIKE);

        reactionRepository.save(reaction);

        return "Post liked successfully!";
    }

    public String unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reaction reaction = reactionRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new RuntimeException("You have not liked this post!"));

        reactionRepository.delete(reaction);

        return "Post unliked successfully!";
    }

    public long getLikeCount(Long postId) {
        return reactionRepository.countByPostId(postId);
    }

    public boolean ifUserLikedPost(Long postId, Long userId) {
        boolean hasLiked = reactionRepository.findByPostIdAndUserId(postId, userId)
                .filter(reaction -> reaction.getReactionType().equals("LIKE"))
                .isPresent();
        return hasLiked;
    }
}

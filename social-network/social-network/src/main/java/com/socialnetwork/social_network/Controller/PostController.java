package com.socialnetwork.social_network.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialnetwork.social_network.DTO.PostSearchDto;
import com.socialnetwork.social_network.DTO.UserConnDto;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.PostRepository;
import com.socialnetwork.social_network.Repository.ReactionRepository;
import com.socialnetwork.social_network.Service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public PostController(PostService postService, PostRepository postRepository, ReactionRepository reactionRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.reactionRepository = reactionRepository;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createPost(@PathVariable Long userId, @RequestBody String content) {
        String message = postService.createPost(userId, content);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostSearchDto>> getPostsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }

    @GetMapping("/feed")
    public List<PostSearchDto> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
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

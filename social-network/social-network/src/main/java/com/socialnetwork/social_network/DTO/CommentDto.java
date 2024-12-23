package com.socialnetwork.social_network.DTO;

import java.time.LocalDateTime;

public class CommentDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private PostDto postOwner;  // User who created the post
    private UserConnDto commenter; // User who made the comment

    public CommentDto(Long id, String content, LocalDateTime createdAt, UserConnDto commenter, PostDto postOwner) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.postOwner = postOwner;
        this.commenter = commenter;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PostDto getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(PostDto postOwner) {
        this.postOwner = postOwner;
    }

    public UserConnDto getCommenter() {
        return commenter;
    }

    public void setCommenter(UserConnDto commenter) {
        this.commenter = commenter;
    }
}

package com.socialnetwork.social_network.DTO;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostSearchDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserConnDto user; // User who created the post
    private long likes;

    public PostSearchDto(Long id, String content, LocalDateTime createdAt, UserConnDto user, long likes) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.likes = likes;
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

    public UserConnDto getUser() {
        return user;
    }

    public void setUser(UserConnDto user) {
        this.user = user;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public long getLikes() {
        return likes;
    }

}

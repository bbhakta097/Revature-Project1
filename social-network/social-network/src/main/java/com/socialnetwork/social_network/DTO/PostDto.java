package com.socialnetwork.social_network.DTO;

import java.time.LocalDateTime;

public class PostDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserConnDto user;
    

    public PostDto(Long id, String content, LocalDateTime createdAt, UserConnDto user) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
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
}

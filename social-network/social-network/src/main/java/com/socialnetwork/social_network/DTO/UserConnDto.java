package com.socialnetwork.social_network.DTO;

public class UserConnDto {
    private Long userId;
    private String username;

    public UserConnDto(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

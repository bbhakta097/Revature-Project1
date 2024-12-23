package com.socialnetwork.social_network.DTO;

public class UserSearchDto {
    private Long id;
    private String username;

    public UserSearchDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

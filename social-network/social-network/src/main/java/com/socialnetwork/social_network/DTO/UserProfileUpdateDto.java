package com.socialnetwork.social_network.DTO;

public class UserProfileUpdateDto {
    private String username;
    private String email;
    private String password;
    private String bio;

    public UserProfileUpdateDto(String string, String string2, String string3, String string4) {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}

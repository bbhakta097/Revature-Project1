package com.socialnetwork.social_network.Controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialnetwork.social_network.DTO.AuthRequest;
import com.socialnetwork.social_network.DTO.AuthenticationResponse;
import com.socialnetwork.social_network.DTO.RegisterRequest;
import com.socialnetwork.social_network.DTO.UserConnDto;
import com.socialnetwork.social_network.DTO.UserProfileUpdateDto;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.UserRepository;
import com.socialnetwork.social_network.Service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    // private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {

        try {
            AuthenticationResponse token = authService.registerUser(request);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Registration has failed." + e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest auth) {
        try {
            return ResponseEntity.ok(authService.loginUser(auth));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUserProfile(@PathVariable String username) {
        try {
            return ResponseEntity.ok(authService.getUserProfile(username));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error retrieving user profile: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while retrieving the user profile.");
        }
    }

    @PutMapping("/users/{userId}/update")
    public ResponseEntity<String> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileUpdateDto updateDto) {
        try {
            System.out.println("We have reached hereeeee");
            authService.updateUserProfile(userId, updateDto);
            return ResponseEntity.ok("User profile updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error updating profile: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while updating the profile.");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserConnDto> userDto = users.stream()
                    .map(user -> new UserConnDto(user.getId(), user.getUsername()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }

    }

}

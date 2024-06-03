package com.example.task.controller;

import com.example.task.entity.User;
import com.example.task.service.AuthServiceImpl;
import com.example.task.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            logger.debug("Received login request for username: {}", user.getUsername());
            userService.loadUserByUsername(user.getUsername());
            return ResponseEntity.ok().build();
        } catch (UsernameNotFoundException e) {
            logger.error("Invalid username or password", e);
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/preferences")
    public ResponseEntity<?> getPreferences(@RequestHeader("Authorization") String token) {
        try {
            String username = userService.extractUsernameFromToken(token);
            Map<String, String> preferences = userService.getUserPreferences(username);
            return ResponseEntity.ok(preferences);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
    }

    @PutMapping("/preferences")
    public ResponseEntity<?> updatePreferences(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> preferences) {
        try {
            String username = userService.extractUsernameFromToken(token);
            userService.updateUserPreferences(username, preferences);
            return ResponseEntity.ok("Preferences updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
    }
}

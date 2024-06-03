package com.example.task.service;

import com.example.task.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User saveUser(User user);
    Optional<User> findByUsername(String username);
    UserDetails loadUserByUsername(String username);
    String extractUsernameFromToken(String token);
    Map<String, String> getUserPreferences(String username);
    void updateUserPreferences(String username, Map<String, String> preferences);
}

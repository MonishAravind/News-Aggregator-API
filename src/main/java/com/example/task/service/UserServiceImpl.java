package com.example.task.service;

import com.example.task.entity.User;
import com.example.task.repo.UserRepository;
import com.example.task.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private Map<String, Map<String, String>> userPreferences = new HashMap<>();

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        logger.info("User saved successfully: {}", user.getUsername());
        return savedUser;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        logger.debug("Finding user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user;
    }

    @Override
    public String extractUsernameFromToken(String token) {
        logger.debug("Extracting username from token");
        return jwtUtil.extractUsername(token);
    }

    @Override
    public Map<String, String> getUserPreferences(String username) {
        logger.debug("Getting user preferences for username: {}", username);
        return userPreferences.getOrDefault(username, new HashMap<>());
    }

    @Override
    public void updateUserPreferences(String username, Map<String, String> preferences) {
        logger.debug("Updating user preferences for username: {}", username);
        userPreferences.put(username, preferences);
    }
}

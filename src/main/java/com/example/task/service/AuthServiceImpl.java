package com.example.task.service;

import com.example.task.entity.*;
import com.example.task.repo.TokenRepository;
import com.example.task.repo.UserRepository;
import com.example.task.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository repository;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;



    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        logger.info("Registering user: {}", request.getUsername());
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User savedUser = userService.saveUser(user);
        String jwtToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        logger.info("User registered successfully: {}", request.getUsername());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            logger.info("Authenticating user: {}", request.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            User user = repository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getUsername()));
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

            String jwtToken = jwtUtil.generateToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            logger.info("User authenticated successfully: {}", request.getUsername());
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", request.getUsername(), e);
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }


    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Invalid or missing Authorization header");
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtUtil.extractUsername(refreshToken);
        if (userName != null) {
            User user = repository.findByUsername(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));
            if (jwtUtil.isTokenValid(refreshToken, user)) {
                String accessToken = jwtUtil.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                logger.info("Token refreshed successfully for user: {}", userName);
            } else {
                logger.warn("Invalid refresh token for user: {}", userName);
            }
        } else {
            logger.warn("Username extraction failed from refresh token");
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
        logger.debug("Token saved for user: {}", user.getUsername());
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
        if (validUserTokens.isEmpty()) {
            logger.debug("No valid tokens found for user: {}", user.getUsername());
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
        logger.debug("All tokens revoked for user: {}", user.getUsername());
    }
}

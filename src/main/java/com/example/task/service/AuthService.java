package com.example.task.service;

import com.example.task.entity.AuthenticationRequest;
import com.example.task.entity.AuthenticationResponse;
import com.example.task.entity.RegisterRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}

package com.enigma.service;

import com.enigma.model.DTO.LoginRequest;
import com.enigma.model.DTO.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest registerRequest);
    String login(LoginRequest loginRequest);
}

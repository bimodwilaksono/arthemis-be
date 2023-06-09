package com.enigma.service;

import com.enigma.exceptions.TokenRefreshException;
import com.enigma.model.RefreshToken;
import com.enigma.model.request.LoginRequest;
import com.enigma.model.request.RegisterRequest;
import com.enigma.model.User;
import com.enigma.model.response.EntityResponse.JwtResponse;
import com.enigma.repository.UserRepository;
import com.enigma.utils.JwtUtil;
import com.enigma.utils.PassEncoder;
import com.enigma.utils.constants.Role;
import de.mkammerer.argon2.Argon2;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final PassEncoder passEncoder;

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthService(UserRepository userRepository, ModelMapper modelMapper, JwtUtil jwtUtil, PassEncoder passEncoder, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.passEncoder = passEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public JwtResponse register(RegisterRequest registerRequest) {
        try {
            User user = modelMapper.map(registerRequest, User.class);
            user.setPassword(passEncoder.hashPassword(registerRequest.getPassword()));
            user.setRole(Role.User);
            User userRequest = userRepository.save(user);
            RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(userRequest.getId());


            String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(token);
            jwtResponse.setRefreshToken(refreshToken.getRefreshToken());
            return jwtResponse;
        } catch (DataAccessException e) {
            throw new EntityExistsException(e.getMessage());
        }
    }

    public JwtResponse login(LoginRequest loginRequest) {
        try {
            User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("email or password is incorrect"));

            if (!passEncoder.verifyPassword(user.getPassword(), loginRequest.getPassword())) {
                throw new RuntimeException("email or password is incorrect");
            }
            RefreshToken refreshToken = this.refreshTokenService.findByUserId(user.getId())
                    .orElseThrow(() -> new TokenRefreshException(null, "Refresh token is not in database"));
            String token = jwtUtil.generateToken(loginRequest.getEmail(), user.getRole(), user.getId());
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(token);
            jwtResponse.setRefreshToken(refreshToken.getRefreshToken());
            return jwtResponse;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public JwtResponse loginAdmin(LoginRequest loginRequest) {
        try {
            User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("email or password is incorrect"));

            if (!passEncoder.verifyPassword(user.getPassword(), loginRequest.getPassword())) {
                throw new RuntimeException("email or password is incorrect");
            }

            // Check if the user role is admin
            if (!user.getRole().equals(Role.Admin)) {
                throw new RuntimeException("You do not have admin privileges");
            }
            RefreshToken refreshToken = this.refreshTokenService.findByUserId(user.getId())
                    .orElseThrow(() -> new TokenRefreshException(null, "Refresh token is not in database"));
            String token = jwtUtil.generateToken(loginRequest.getEmail(), user.getRole(), user.getId());
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(token);
            jwtResponse.setRefreshToken(refreshToken.getRefreshToken());
            return jwtResponse;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public User changeUserRole(String userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);


        return userRepository.save(user);
    }
}

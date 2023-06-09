package com.enigma.controller;

import com.enigma.exceptions.TokenRefreshException;
import com.enigma.model.RefreshToken;
import com.enigma.model.request.ChangeUserRoleRequest;
import com.enigma.model.request.LoginRequest;
import com.enigma.model.request.RegisterRequest;
import com.enigma.model.User;
import com.enigma.model.request.TokenRefreshRequest;
import com.enigma.model.response.EntityResponse.JwtResponse;
import com.enigma.model.response.EntityResponse.TokenRefreshResponse;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.AuthService;
import com.enigma.service.RefreshTokenService;
import com.enigma.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    private RefreshTokenService refreshTokenService;

    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        JwtResponse jwtResponse = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Registration Success", jwtResponse));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Login Success", jwtResponse));
    }

    @PostMapping("/login-admin")
    public ResponseEntity loginAdmin(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.loginAdmin(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Login Success", jwtResponse));
    }


    @PutMapping("/user-role")
    public ResponseEntity changeUserRole(@Valid @RequestBody ChangeUserRoleRequest changeUserRoleRequest) {
        User updatedUser = authService.changeUserRole(changeUserRoleRequest.getUserId(), changeUserRoleRequest.getNewRole());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("User role updated", updatedUser));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
                    TokenRefreshResponse tokenRefreshResponse = new TokenRefreshResponse();
                    tokenRefreshResponse.setAccessToken(token);
                    tokenRefreshResponse.setRefreshToken(requestRefreshToken);

                    return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success get new refresh token", tokenRefreshResponse));
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database"))
                ;
    }
}

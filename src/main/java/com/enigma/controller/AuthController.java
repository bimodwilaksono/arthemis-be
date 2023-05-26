package com.enigma.controller;

import com.enigma.model.DTO.ChangeUserRoleRequest;
import com.enigma.model.DTO.LoginRequest;
import com.enigma.model.DTO.RegisterRequest;
import com.enigma.model.User;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest){
        String token = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Registration Success", token));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
        String token = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Login Success", token));
    }

    @PostMapping("/login-admin")
    public ResponseEntity loginAdmin(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.loginAdmin(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Login Success", token));
    }


    @PutMapping("/user-role")
    public ResponseEntity changeUserRole(@Valid @RequestBody ChangeUserRoleRequest changeUserRoleRequest){
        User updatedUser = authService.changeUserRole(changeUserRoleRequest.getUserId(), changeUserRoleRequest.getNewRole());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("User role updated", updatedUser));
    }
}

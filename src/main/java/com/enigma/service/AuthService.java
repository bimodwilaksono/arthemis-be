package com.enigma.service;

import com.enigma.model.DTO.LoginRequest;
import com.enigma.model.DTO.RegisterRequest;
import com.enigma.model.User;
import com.enigma.repository.UserRepository;
import com.enigma.utils.JwtUtil;
import com.enigma.utils.constants.Role;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, ModelMapper modelMapper, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }

    public String register(RegisterRequest registerRequest){
        try{
            User user = modelMapper.map(registerRequest, User.class);
            user.setRole(Role.User);
            User userRequest = userRepository.save(user);

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
            return token;
        }catch (DataAccessException e){
            throw new EntityExistsException(e.getMessage());
        }
    }

    public String login(LoginRequest loginRequest){
        try{
            User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("email or password is incorrect"));

            if (!user.getPassword().equals(loginRequest.getPassword())) {
                throw new RuntimeException("email or password is incorrect");
            }
            String token = jwtUtil.generateToken(loginRequest.getEmail(), user.getRole());
            return token;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public String loginAdmin(LoginRequest loginRequest) {
        try {
            User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("email or password is incorrect"));

            if (!user.getPassword().equals(loginRequest.getPassword())) {
                throw new RuntimeException("email or password is incorrect");
            }

            // Check if the user role is admin
            if (!user.getRole().equals(Role.Admin)) {
                throw new RuntimeException("You do not have admin privileges");
            }

            String token = jwtUtil.generateToken(loginRequest.getEmail(), user.getRole());
            return token;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public User changeUserRole(String userId, Role newRole){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);


        return userRepository.save(user);
    }
}

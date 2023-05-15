package com.enigma.service;

import com.enigma.model.DTO.LoginRequest;
import com.enigma.model.DTO.RegisterRequest;
import com.enigma.model.User;
import com.enigma.repository.UserRepository;
import com.enigma.utils.JwtUtil;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            User userRequest = userRepository.save(user);

            String token = jwtUtil.generateToken(user.getEmail());
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
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            return token;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}

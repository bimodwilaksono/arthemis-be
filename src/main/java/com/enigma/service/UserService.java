package com.enigma.service;

import com.enigma.model.DTO.ChangePassword;
import com.enigma.model.DTO.ProfileUploadRequest;
import com.enigma.model.DTO.ChangeUserNameEmailRequest;
import com.enigma.model.User;
import com.enigma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;

    @Autowired
    public UserService(UserRepository userRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    public Page<User> findAll(
            Integer page, Integer size,
            String direction, String sort
    ){
        try{
            Sort sortBy = Sort.by(Sort.Direction.valueOf(direction), sort);
            Pageable pageable = PageRequest.of(page-1,size, sortBy);
            Page<User> userList = (Page<User>) userRepository.findAll(pageable);
            return userList;
        }catch (Exception e){
            throw new RuntimeException("Failed to find all user: "+ e.getMessage());
        }
    }

    public User findById(String id){
        try{
            return userRepository.findById(id).orElseThrow(() -> new RuntimeException("id: "+id+" Does Not Exists"));
        }catch (Exception e){
            throw new RuntimeException("Failed to find user by id: "+ e.getMessage());
        }
    }

    public User updateNameAndEmail(String id, ChangeUserNameEmailRequest user){
        try {
            User existingUser = findById(id);
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        }catch (Exception e){
            throw new RuntimeException("Failed to update user: "+e.getMessage());
        }
    }

    public User updatePassword(String id, ChangePassword changePassword){
        try{
            User existingUser = findById(id);
            existingUser.setPassword(changePassword.getPassword());
            return userRepository.save(existingUser);
        }catch (Exception e){
            throw new RuntimeException("Failed to update password "+e.getMessage());
        }
    }

    public User UpdateProfile(String id, ProfileUploadRequest user){
        try {
            User existingUser = findById(id);
            String filePath = "";

            if (!user.getProfilePic().isEmpty()){
                filePath = fileService.uploadFile(user.getProfilePic());
            }
            existingUser.setProfilePic(filePath);
            return userRepository.save(existingUser);
        }catch (Exception e){
            throw new RuntimeException("Failed to update Profile: "+e.getMessage());
        }
    }

    public void delete(String id){
        try {
            User user = findById(id);
            userRepository.delete(user);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}

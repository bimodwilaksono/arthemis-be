package com.enigma.service;

import com.enigma.model.Campsite;
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
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findAll(
            Integer page, Integer size,
            String direction, String sort
    ){
        try{
            Sort sortBy = Sort.by(Sort.Direction.valueOf(direction), sort);
            Pageable pageable = PageRequest.of(page-1,size, sortBy);
            Page<User> userList = (Page<User>) userRepository.findAll(pageable);
            if (userList.isEmpty()){
                throw new RuntimeException("Database Empty");
            }
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

    public User update(String id, User user){
        try {
            User existingUser = findById(id);
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }catch (Exception e){
            throw new RuntimeException("Filed to update user: "+e.getMessage());
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

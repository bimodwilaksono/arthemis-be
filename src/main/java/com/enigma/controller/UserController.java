package com.enigma.controller;

import com.enigma.model.DTO.ChangePassword;
import com.enigma.model.DTO.ProfileUploadRequest;
import com.enigma.model.DTO.ChangeUserNameEmailRequest;
import com.enigma.model.User;
import com.enigma.model.response.CommonResponse;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity getAllUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "id") String sort
    ){
        Page<User> userList = userService.findAll(page, size, direction, sort);
        CommonResponse commonResponse = new SuccessResponse<>("Success Get All user", userList);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") String id){
        User user = userService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Finding user with id: "+id, user);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }


    @PatchMapping("/update-email/{id}")
    public ResponseEntity updateUser(@PathVariable("id") String id, @RequestBody ChangeUserNameEmailRequest user){
        User updateUser = modelMapper.map(user, User.class);
        userService.updateNameAndEmail(id, user);
        CommonResponse commonResponse = new SuccessResponse<>("Success updating user", updateUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PatchMapping("/update-profile/{id}")
    public ResponseEntity updateProfileUser(@PathVariable("id") String id, @Valid ProfileUploadRequest userRequest){
        User UpdateProfile = modelMapper.map(userRequest, User.class);
        userService.UpdateProfile(id, userRequest);
        CommonResponse commonResponse = new SuccessResponse<>("Success Updating user profile",UpdateProfile);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PatchMapping("/change-password/{id}")
    public ResponseEntity updatPassword(@PathVariable("id") String id, @RequestBody ChangePassword changePassword){
        User updatePassword = modelMapper.map(changePassword, User.class);
        userService.updatePassword(id, changePassword);
        CommonResponse commonResponse = new SuccessResponse<>("Success Updating user password",updatePassword);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success deleting user",null));
    }
}

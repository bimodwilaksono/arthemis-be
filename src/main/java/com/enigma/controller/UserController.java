package com.enigma.controller;

import com.enigma.model.User;
import com.enigma.model.response.CommonResponse;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAllCamps(
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
    public ResponseEntity getCampById(@PathVariable("id") String id){
        User user = userService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Finding user with id: "+id, user);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity updateCamp(@PathVariable("id") String id, @RequestBody User user){
        User updateUser = userService.update(id, user);
        CommonResponse commonResponse = new SuccessResponse<>("Success updating product", updateUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCamp(@PathVariable String id){
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success deleting campsite",null));
    }
}

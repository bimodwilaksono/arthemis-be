package com.enigma.controller;

import com.enigma.model.DTO.RegisterRequest;
import com.enigma.model.Payment;
import com.enigma.model.Rating;
import com.enigma.model.response.CommonResponse;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.AuthService;
import com.enigma.service.PaymentService;
import com.enigma.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity getAllRating(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "id") String sort
    ){
        Page<Rating> ratings = ratingService.findAll(page, size, direction, sort);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success Get All rating", ratings));
    }

    @GetMapping("/{id}")
    public ResponseEntity getByid(@PathVariable("id") String id){
        Rating rating = ratingService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Finding rating with id: "+id, rating);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Rating rating){
        Rating createRating = ratingService.save(rating);
        CommonResponse commonResponse = new SuccessResponse<>("Success Creating new rating", createRating);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOrder(@PathVariable("id") String id, @RequestBody Rating rating){
        Rating update = ratingService.update(id, rating);
        CommonResponse commonResponse = new SuccessResponse<>("Success updating rating", update);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @DeleteMapping
    public ResponseEntity deleteOrder(@PathVariable String id){
        ratingService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success deleting rating",null));
    }
}

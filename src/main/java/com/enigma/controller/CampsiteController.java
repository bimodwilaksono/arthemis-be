package com.enigma.controller;

import com.enigma.model.Campsite;
import com.enigma.model.response.CommonResponse;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.CampsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/campsite")
public class CampsiteController {

    private final CampsiteService campsiteService;

    @Autowired
    public CampsiteController(CampsiteService campsiteService) {
        this.campsiteService = campsiteService;
    }
    @GetMapping
    public ResponseEntity getAllCamps(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "id") String sort
    ){
        Page<Campsite> campsiteList = campsiteService.findAll(page, size, direction, sort);
        CommonResponse commonResponse = new SuccessResponse<>("Success Get All Campsite", campsiteList);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCampById(@PathVariable("id") String id){
        Campsite campsite = campsiteService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Finding campsite with id: "+id, campsite);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity createCampsite(@RequestBody Campsite campsite){
        Campsite createCamp = campsiteService.save(campsite);
        CommonResponse commonResponse = new SuccessResponse<>("Success Creating new Campsite", createCamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateCamp(@PathVariable("id") String id, @RequestBody Campsite campsite){
        Campsite updateCamp = campsiteService.update(id, campsite);
        CommonResponse commonResponse = new SuccessResponse<>("Success updating product", updateCamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @DeleteMapping
    public ResponseEntity deleteCamp(@PathVariable String id){
        campsiteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success deleting campsite",null));
    }
}

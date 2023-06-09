package com.enigma.controller;

import com.enigma.model.Campsite;
import com.enigma.model.Order;
import com.enigma.model.response.CommonResponse;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity getAllOrder(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "id") String sort
    ){
        Page<Order> orders = orderService.findAll(page, size, direction, sort);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success Get All order", orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity getByid(@PathVariable("id") String id){
        Order order = orderService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Finding order with id: "+id, order);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody Order order){
        Order createdOrder = orderService.save(order);
        CommonResponse commonResponse = new SuccessResponse<>("Success Creating new Order", createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateOrder(@PathVariable("id") String id, @RequestBody Order order){
        Order updatedorder = orderService.update(id, order);
        CommonResponse commonResponse = new SuccessResponse<>("Success updating Order", updatedorder);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @DeleteMapping
    public ResponseEntity deleteOrder(@PathVariable String id){
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success deleting Order",null));
    }
}

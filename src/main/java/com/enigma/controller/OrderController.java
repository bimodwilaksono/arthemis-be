package com.enigma.controller;

import com.enigma.model.request.OrderRequest;
import com.enigma.model.Order;
import com.enigma.model.request.OrderStatusUpdateRequest;
import com.enigma.model.request.OrderUpdateRequest;
import com.enigma.model.response.CommonResponse;
import com.enigma.model.response.EntityResponse.OrderResponse;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.OrderService;
import com.enigma.utils.JwtUtil;
import com.enigma.utils.constants.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    @Autowired
    public OrderController(OrderService orderService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity getAllOrders(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "id") String sort,
            @RequestHeader("Authorization") String token
    ){
        // Extract userId from token
        String bearerToken = token.substring(7); // Remove 'Bearer ' from token
        String userId = jwtUtil.getUserIdFromToken(bearerToken);
        Role role = jwtUtil.getRoleFromToken(bearerToken);

        Page<OrderResponse> orders = orderService.findAll(page, size, direction, sort, userId,role);
        CommonResponse commonResponse = new SuccessResponse<>("Success Get All orders", orders);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOrderById(
            @PathVariable String id,
            @RequestHeader("Authorization") String token
    ){
        // Extract email from token
        String bearerToken = token.substring(7); // Remove 'Bearer ' from token
        String userIdFromToken = jwtUtil.getUserIdFromToken(bearerToken);
        Role role = jwtUtil.getRoleFromToken(bearerToken);


        OrderResponse order = orderService.findByIdForUser(id, userIdFromToken, role);
        CommonResponse commonResponse = new SuccessResponse<>("Success Get Order", order);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }


    @PostMapping
    public ResponseEntity createOrder(@Valid  @RequestBody OrderRequest orderDto){
        Order createdOrder = orderService.save(orderDto);
        CommonResponse commonResponse = new SuccessResponse<>("Success Creating new Order", createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOrder(@PathVariable("id") String id,@Valid @RequestBody OrderUpdateRequest orderRequest){
        Order updatedOrder = orderService.update(id, orderRequest);
        CommonResponse commonResponse = new SuccessResponse<>("Success updating Order", updatedOrder);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }


    @PutMapping("/status/{id}")
    public ResponseEntity changeOrderStatus(
            @PathVariable String id,
            @RequestBody OrderStatusUpdateRequest statusUpdateRequest
    ) {
        OrderResponse updatedOrder = orderService.changeOrderStatus(id, statusUpdateRequest);
        CommonResponse commonResponse = new SuccessResponse<>("Success updating Order Status", updatedOrder);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable String id){
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success deleting Order",null));
    }
}

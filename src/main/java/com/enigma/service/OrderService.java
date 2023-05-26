package com.enigma.service;

import com.enigma.model.Campsite;
import com.enigma.model.DTO.OrderRequest;
import com.enigma.model.Order;
import com.enigma.model.User;
import com.enigma.repository.CampsiteRepository;
import com.enigma.repository.OrderRepository;
import com.enigma.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CampsiteRepository campsiteRepository;
    private UserRepository userRepository;

    private final ModelMapper modelMapper;


    public OrderService(OrderRepository orderRepository, CampsiteRepository campsiteRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.campsiteRepository = campsiteRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Order> findAll(
            Integer page, Integer size,
            String direction, String sort
    ){
        try{
            Sort sortBy = Sort.by(Sort.Direction.valueOf(direction), sort);
            Pageable pageable = PageRequest.of((page-1),size,sortBy);
            Page<Order> orders = orderRepository.findAll(pageable);
            return orders;
        }catch (Exception e){
            throw new RuntimeException("Failed to find all orders: "+e.getMessage());
        }
    }

    public Order findById(String id){
        try{
            return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("id "+ id + " Does Not exists"));
        }catch (Exception e){
            throw new RuntimeException("Failed to find order by id: "+ e.getMessage());
        }
    }

    @Transactional
    public Order save(OrderRequest orderDto) {
        try{
            Optional<User> userOptional = userRepository.findById(orderDto.getUserId());
            if (userOptional.isEmpty()){
                throw new RuntimeException("User not found with id " + orderDto.getUserId());
            }

            Optional<Campsite> campsiteOptional = campsiteRepository.findById(orderDto.getCampsiteId());
            if (campsiteOptional.isEmpty()){
                throw new RuntimeException("Campsite not found with id " + orderDto.getCampsiteId());
            }

            Order order = modelMapper.map(orderDto, Order.class);
            order.setUser(userOptional.get());
            order.setCampsite(campsiteOptional.get());
            Campsite campsite = campsiteOptional.get();
            campsite.getOrder().add(order);

            // save the updated campsite
            campsiteRepository.save(campsite);

            return orderRepository.save(order);
        } catch (Exception e){
            throw new RuntimeException("Failed to save order: "+e.getMessage());
        }
    }


    @Transactional
    public Order update(String id, OrderRequest orderDto){
        try {
            Order existsOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

            User user = userRepository.findById(orderDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Campsite campsite = campsiteRepository.findById(orderDto.getCampsiteId()).orElseThrow(() -> new RuntimeException("Campsite not found"));

            // update fields from orderDto to existsOrder, and set user and campsite
            existsOrder.setIsCheckOut(orderDto.getIsCheckOut());
            existsOrder.setCheckInDate(orderDto.getCheckInDate());
            existsOrder.setCheckOutDate(orderDto.getCheckOutDate());
            existsOrder.setUser(user);
            existsOrder.setCampsite(campsite);

            // add the updated order to the campsite's order list
            campsite.getOrder().add(existsOrder);

            // save the updated campsite
            campsiteRepository.save(campsite);

            return orderRepository.save(existsOrder);
        }catch (Exception e){
            throw new RuntimeException("Failed to Update order, "+e.getMessage());
        }
    }

    public void delete(String id){
        try {
            Order order = findById(id);
            orderRepository.delete(order);
        }catch (Exception e){
            throw new RuntimeException("Failed to delete Order: "+e.getMessage());
        }
    }
}

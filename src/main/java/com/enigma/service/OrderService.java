package com.enigma.service;

import com.enigma.model.Campsite;
import com.enigma.model.Order;
import com.enigma.repository.CampsiteRepository;
import com.enigma.repository.OrderRepository;
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

    @Autowired
    public OrderService(OrderRepository orderRepository, CampsiteRepository campsiteRepository) {
        this.orderRepository = orderRepository;
        this.campsiteRepository = campsiteRepository;
    }

    public Page<Order> findAll(
            Integer page, Integer size,
            String direction, String sort
    ){
        try{
            Sort sortBy = Sort.by(Sort.Direction.valueOf(direction), sort);
            Pageable pageable = PageRequest.of((page-1),size,sortBy);
            Page<Order> orders = orderRepository.findAll(pageable);
            if (orders.isEmpty()){
                throw new RuntimeException("Database Empty");
            }
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

    public Order save(Order order){
        try{
            Optional<Campsite> campsite = campsiteRepository.findById(order.getCampsite().getId());
            if (campsite.isEmpty()){
                throw new RuntimeException("Failed to find camp with id " + order.getCampsite().getId());
            }
            return orderRepository.save(order);
        }catch (Exception e){
            throw new RuntimeException("Failed to save order: "+e.getMessage());
        }
    }

    public Order update(String id, Order order){
        try {
            Order existsOrder = findById(id);
            existsOrder.setStatus(order.getStatus());
            existsOrder.setCheckInDate(order.getCheckInDate());
            existsOrder.setCheckOutDate(order.getCheckOutDate());
            existsOrder.setUser(order.getUser());
            existsOrder.setCampsite(order.getCampsite());

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

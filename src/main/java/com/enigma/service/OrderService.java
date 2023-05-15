package com.enigma.service;

import com.enigma.model.Campsite;
import com.enigma.model.Order;
import com.enigma.repository.CampsiteRepository;
import com.enigma.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Order> findAll(){
        try{
            List<Order> orders = orderRepository.findAll();
            if (orders.isEmpty()){
                throw new RuntimeException("Database Empty");
            }
            return orders;
        }catch (Exception e){
            throw new RuntimeException("Failed to find all orders: "+e.getMessage());
        }
    }

    public Order findById(Integer id){
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

    public Order update(Integer id, Order order){
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

    public void delete(Integer id){
        try {
            Order order = findById(id);
            orderRepository.delete(order);
        }catch (Exception e){
            throw new RuntimeException("Failed to delete Order: "+e.getMessage());
        }
    }
}

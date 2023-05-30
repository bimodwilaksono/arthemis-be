package com.enigma.service;

import com.enigma.model.Campsite;
import com.enigma.model.Payment;
import com.enigma.model.request.OrderRequest;
import com.enigma.model.Order;
import com.enigma.model.User;
import com.enigma.model.request.OrderStatusUpdateRequest;
import com.enigma.model.request.OrderUpdateRequest;
import com.enigma.model.response.EntityResponse.OrderResponse;
import com.enigma.repository.CampsiteRepository;
import com.enigma.repository.OrderRepository;
import com.enigma.repository.PaymentRepository;
import com.enigma.repository.UserRepository;
import com.enigma.utils.JwtUtil;
import com.enigma.utils.constants.PaymentStatus;
import com.enigma.utils.constants.Role;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CampsiteRepository campsiteRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final JwtUtil jwtUtil;

    private final ModelMapper modelMapper;


    public OrderService(OrderRepository orderRepository, CampsiteRepository campsiteRepository, UserRepository userRepository, JwtUtil jwtUtil, PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.campsiteRepository = campsiteRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    public Page<OrderResponse> findAll(
            Integer page, Integer size,
            String direction, String sort,
            String userEmail,
            Role role
    ){
        try{
            Sort sortBy = Sort.by(Sort.Direction.valueOf(direction), sort);
            Pageable pageable = PageRequest.of((page-1),size,sortBy);
            Page<Order> orders;

            if (role == Role.User){
                orders =  orderRepository.findByUser_EmailIgnoreCase(userEmail,pageable);
            }else {
                orders = orderRepository.findAll(pageable);
            }
            return orders.map(order -> modelMapper.map(order, OrderResponse.class));
        }catch (Exception e){
            throw new RuntimeException("Failed to find all orders: "+e.getMessage());
        }
    }

    public OrderResponse findById(String id){
        try{
            Order order= orderRepository.findById(id).orElseThrow(() -> new RuntimeException("id "+ id + " Does Not exists"));
            return modelMapper.map(order, OrderResponse.class);
        }catch (Exception e){
            throw new RuntimeException("Failed to find order by id: "+ e.getMessage());
        }
    }

    public OrderResponse findByIdForUser(String id, String userIdFromToken, Role role){
        try{
            Order orders;

            if (role == Role.User){
                orders = orderRepository.findByUser_Id(userIdFromToken);
            }else {
                orders = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("id "+ id + " Does Not exists"));
            }

            return modelMapper.map(orders, OrderResponse.class);
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

            // Create a new Payment for the Order
            Payment payment = new Payment();
            payment.setPaymentMethod(orderDto.getPaymentMethod()); // set the status as required
            payment.setAmount(orderDto.getAmount());
            payment.setStatus(PaymentStatus.PROCESS);

            // Save the Payment to the repository
            paymentRepository.save(payment);
            Order order = modelMapper.map(orderDto, Order.class);
            order.setUser(userOptional.get());
            order.setCampsite(campsiteOptional.get());
            order.setIsCheckIn(false);
            order.setIsCheckOut(false);
            order.setPayment(payment);
            Campsite campsite = campsiteOptional.get();
            campsite.getOrder().add(order);

            return orderRepository.save(order);
        } catch (Exception e){
            throw new RuntimeException("Failed to save order: "+e.getMessage());
        }
    }



    @Transactional
    public Order update(String id, OrderUpdateRequest orderDto){
        try {
            Order existsOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

            User user = userRepository.findById(orderDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Campsite campsite = campsiteRepository.findById(orderDto.getCampsiteId()).orElseThrow(() -> new RuntimeException("Campsite not found"));

            // update fields from orderDto to existsOrder, and set user and campsite
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

    public OrderResponse changeOrderStatus(String id, OrderStatusUpdateRequest orderStatusUpdateRequest){
        try{
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Order with id " + id + " not found"));

            order.setIsCheckIn(orderStatusUpdateRequest.getIsCheckIn());

            Boolean newIsCheckOut = orderStatusUpdateRequest.getIsCheckOut();
            if (newIsCheckOut && !order.getIsCheckOut()) {
                // Jika isCheckOut diubah menjadi true, perbarui checkOutDate
                order.setCheckOutDate(LocalDate.now());
            }
            order.setIsCheckOut(newIsCheckOut);

            Order updatedOrder = orderRepository.save(order);

            return modelMapper.map(updatedOrder, OrderResponse.class);
        } catch (Exception e){
            throw new RuntimeException("Failed to Update status order, "+e.getMessage());
        }
    }



    public void delete(String id){
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("id "+ id + " Does Not exists"));
            orderRepository.delete(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete Order: "+e.getMessage());
        }
    }

}

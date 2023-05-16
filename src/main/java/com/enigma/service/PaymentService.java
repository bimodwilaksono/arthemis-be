package com.enigma.service;

import com.enigma.model.Order;
import com.enigma.model.Payment;
import com.enigma.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private OrderService orderService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    public Page<Payment> findAll(
            Integer page, Integer size,
            String direction, String sort
    ){
        try{
            Sort sortBy = Sort.by(Sort.Direction.valueOf(direction), sort);
            Pageable pageable = PageRequest.of((page-1),size,sortBy);
            Page<Payment> payments = paymentRepository.findAll(pageable);
            if (payments.isEmpty()){
                throw new RuntimeException("Database Empty");
            }
            return payments;
        }catch (Exception e){
            throw new RuntimeException("Failed to find all Payment" + e.getMessage());
        }
    }

    public Payment findById(String id){
        try{
            return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("id "+ id + " Does Not exists"));
        }catch (Exception e){
            throw new RuntimeException("Failed to find payment by id, "+ e.getMessage() );
        }
    }
    @Transactional
    public Payment save(Payment payment){
        try {
            orderService.findById(payment.getOrder().getId());
            return paymentRepository.save(payment);
        }catch (Exception e){
            throw new RuntimeException("Failed to create Payment, "+e.getMessage());
        }
    }

    @Transactional
    public Payment update(String id, Payment payment){
        try {
            Payment existingPayment = findById(id);
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            existingPayment.setStatus(existingPayment.getStatus());
            existingPayment.setAmount(existingPayment.getAmount());

            return paymentRepository.save(existingPayment);
        }catch (Exception e){
            throw new RuntimeException("Failed to Update Payment, "+e.getMessage());
        }
    }

    public void delete(String id){
        try{
            Payment payment = findById(id);
            paymentRepository.delete(payment);
        }catch (Exception e){
            throw new RuntimeException("Failed to Delete Payment, "+e.getMessage());
        }
    }
}

package com.enigma.service;

import com.enigma.model.Order;
import com.enigma.model.Payment;
import com.enigma.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Payment> findAll(){
        try{
            List<Payment> payments = paymentRepository.findAll();
            if (payments.isEmpty()){
                throw new RuntimeException("Database Empty");
            }
            return payments;
        }catch (Exception e){
            throw new RuntimeException("Failed to find all Payment" + e.getMessage());
        }
    }

    public Payment findById(Integer id){
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
    public Payment update(Integer id, Payment payment){
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

    public void delete(Integer id){
        try{
            Payment payment = findById(id);
            paymentRepository.delete(payment);
        }catch (Exception e){
            throw new RuntimeException("Failed to Delete Payment, "+e.getMessage());
        }
    }
}

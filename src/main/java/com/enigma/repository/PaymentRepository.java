package com.enigma.repository;

import com.enigma.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment,Integer> {
    Iterable<Payment> findByPaymentMethod(String paymentMethod);
    Iterable<Payment> findByAmountLessThan(Integer amount);
    Iterable<Payment> findByAmountGreaterThan(Integer amount);
    Iterable<Payment> findByAmountLessThanEqual(Integer amount);
    Iterable<Payment> findByAmountGreaterThanEqual(Integer amount);
    Iterable<Payment> findByStatus(String status);
}

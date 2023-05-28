package com.enigma.repository;

import com.enigma.model.Campsite;
import com.enigma.model.Order;
import com.enigma.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findByUser_EmailIgnoreCase(String email, Pageable pageable);

    Order findByUserEmail(String email);
    Page<Order> findAllByUserId(String userId, Pageable pageable);

    Optional<Object> findByIdAndUserId(String id, String userId);
}

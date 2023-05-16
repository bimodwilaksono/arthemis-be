package com.enigma.repository;

import com.enigma.model.Campsite;
import com.enigma.model.Order;
import com.enigma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    boolean existsByUserAndCampsite(User user, Campsite campsite);

    boolean existsByUserAndCampsiteAndStatus(User user, Campsite campsite, String checkedOut);
}

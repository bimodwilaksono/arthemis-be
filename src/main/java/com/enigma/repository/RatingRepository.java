package com.enigma.repository;

import com.enigma.model.Campsite;
import com.enigma.model.Order;
import com.enigma.model.Rating;
import com.enigma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByCampsite_OrderAndUserAndCampsite(Order order, User user, Campsite campsite);
}

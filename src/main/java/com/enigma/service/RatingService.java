package com.enigma.service;

import com.enigma.model.Rating;
import com.enigma.repository.OrderRepository;
import com.enigma.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private RatingRepository ratingRepository;
    private OrderRepository orderRepository;

    public RatingService(RatingRepository ratingRepository, OrderRepository orderRepository) {
        this.ratingRepository = ratingRepository;
        this.orderRepository = orderRepository;
    }

    public List<Rating> findAll(){
        try{
            List<Rating> ratings = ratingRepository.findAll();
            if (ratings.isEmpty()){
                throw new RuntimeException("Database Empty");
            }
            return ratings;
        }catch (Exception e){
            throw new RuntimeException("Failed to get all rating, "+ e.getMessage());
        }
    }

    public Rating findById(Long id){
        try {
            return ratingRepository.findById(id).orElseThrow(()->new RuntimeException("id "+id+" Does Not Exists"));
        }catch (Exception e){
            throw new RuntimeException("Failed to get rating, "+e.getMessage());
        }
    }

    public Rating save(Rating rating){
        try {
            boolean isUserOrdered = orderRepository.existsByUserAndCampsite(rating.getUser(), rating.getCampsite());
            if (!isUserOrdered) {
                throw new RuntimeException("User belum pernah melakukan order di campsite ini");
            }
            // Validasi apakah user sudah checkout
            boolean isUserCheckedOut = orderRepository.existsByUserAndCampsiteAndStatus(rating.getUser(), rating.getCampsite(), "CHECKED_OUT");
            if (!isUserCheckedOut) {
                throw new RuntimeException("User belum checkout di campsite ini");
            }

            // Validasi apakah user sudah memberikan rating untuk order ini
            boolean isRatingExist = ratingRepository.existsByCampsite_OrderAndUserAndCampsite(rating.getCampsite().getOrder(), rating.getUser(), rating.getCampsite());
            if (isRatingExist) {
                throw new RuntimeException("User sudah memberikan rating untuk order ini");
            }

            return ratingRepository.save(rating);
        }catch (Exception e){
            throw new RuntimeException("Failed to create rating, "+e.getMessage());
        }
    }

    public Rating update(Long id, Rating rating){
        try{
            Rating updateRating = findById(id);
            updateRating.setScore(rating.getScore());
            updateRating.setComment(rating.getComment());
            updateRating.setCampsite(rating.getCampsite());
            updateRating.setUser(rating.getUser());

            return ratingRepository.save(updateRating);
        }catch (Exception e){
            throw new RuntimeException("Failed to update rating, "+e.getMessage());
        }
    }

    public void delete(Long id){
        try {
            Rating rating = findById(id);
            ratingRepository.delete(rating);
        }catch (Exception e){
            throw new RuntimeException("Failed to delete rating, "+ e.getMessage());
        }
    }
}

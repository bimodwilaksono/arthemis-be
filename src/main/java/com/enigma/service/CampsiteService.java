package com.enigma.service;

import com.enigma.model.Campsite;
import com.enigma.repository.CampsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampsiteService {

    private CampsiteRepository campsiteRepository;

    @Autowired
    public CampsiteService(CampsiteRepository campsiteRepository) {
        this.campsiteRepository = campsiteRepository;
    }

    public Page<Campsite> findAll(
            Integer page, Integer size,
            String direction, String sort
    ){
        try{
            Sort sortBy = Sort.by(Sort.Direction.valueOf(direction), sort);
            Pageable pageable = PageRequest.of(page-1,size, sortBy);
            Page<Campsite> campsiteList = (Page<Campsite>) campsiteRepository.findAll(pageable);
            if (campsiteList.isEmpty()){
                throw new RuntimeException("Database Empty");
            }
            return campsiteList;
        }catch (Exception e){
            throw new RuntimeException("Failed to find all camp: "+ e.getMessage());
        }
    }

    public Campsite findById(String id){
        try{
            return campsiteRepository.findById(id).orElseThrow(() -> new RuntimeException("id: "+id+" Does Not Exists"));
        }catch (Exception e){
            throw new RuntimeException("Failed to find camp by id: "+ e.getMessage());
        }
    }

    public Campsite save(Campsite campsite){
        try{
            return campsiteRepository.save(campsite);
        }catch (Exception e){
            throw new RuntimeException("Failed to save camp: "+ e.getMessage());
        }
    }

    public Campsite update(String id, Campsite campsite){
        try {
            Campsite existingCamp = findById(id);
            existingCamp.setName(campsite.getName());
            existingCamp.setLocation(campsite.getLocation());
            existingCamp.setRatings(campsite.getRatings());
            existingCamp.setOrder(campsite.getOrder());

            return campsiteRepository.save(existingCamp);
        }catch (Exception e){
            throw new RuntimeException("Fieled to update camp: "+e.getMessage());
        }
    }

    public void delete(String id){
        try {
            Campsite campsite = findById(id);
            campsiteRepository.delete(campsite);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}

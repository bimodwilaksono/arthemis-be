package com.enigma.service;

import com.enigma.model.Campsite;
import com.enigma.repository.CampsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampsiteService {

    private CampsiteRepository campsiteRepository;

    @Autowired
    public CampsiteService(CampsiteRepository campsiteRepository) {
        this.campsiteRepository = campsiteRepository;
    }

    public List<Campsite> findAll(){
        try{
            List<Campsite> campsiteList = campsiteRepository.findAll();
            if (campsiteList.isEmpty()){
                throw new RuntimeException("Database Empty");
            }
            return campsiteList;
        }catch (Exception e){
            throw new RuntimeException("Failed to find all camp: "+ e.getMessage());
        }
    }

    public Campsite findById(Integer id){
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

    public Campsite update(Integer id, Campsite campsite){
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

    public void delete(Integer id){
        try {
            Campsite campsite = findById(id);
            campsiteRepository.delete(campsite);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}

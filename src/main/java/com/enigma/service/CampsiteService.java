package com.enigma.service;

import com.enigma.model.Campsite;
import com.enigma.model.request.CampsiteRequest;
import com.enigma.repository.CampsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CampsiteService {

    private final CampsiteRepository campsiteRepository;
    private final FileService fileService;

    @Autowired
    public CampsiteService(CampsiteRepository campsiteRepository,
                           FileService fileService) {
        this.campsiteRepository = campsiteRepository;
        this.fileService = fileService;
    }

    public Page<Campsite> findAll(
            Integer page, Integer size,
            String direction, String sort
    ){
        try{
            Sort sortBy = Sort.by(Sort.Direction.valueOf(direction), sort);
            Pageable pageable = PageRequest.of(page-1,size, sortBy);
            Page<Campsite> campsiteList = (Page<Campsite>) campsiteRepository.findAll(pageable);
            return campsiteList;
        }catch (Exception e){
            throw new RuntimeException("Failed to find all camp: "+ e.getMessage());
        }
    }

    public Page<Campsite> findByProvince(
            String province,
            Integer page, Integer size,
            String direction, String sort
    ){
        try {
            Sort sortBy = Sort.by(Sort.Direction.valueOf(direction), sort);
            Pageable pageable = PageRequest.of(page-1,size, sortBy);
            return campsiteRepository.findByProvinceIgnoreCase(province, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find camp by province: " + e.getMessage());
        }
    }

    public Campsite findById(String id){
        try{
            return campsiteRepository.findById(id).orElseThrow(() -> new RuntimeException("id: "+id+" Does Not Exists"));
        }catch (Exception e){
            throw new RuntimeException("Failed to find camp by id: "+ e.getMessage());
        }
    }

    public Campsite save(CampsiteRequest campsiteRequest){
        try{
            String filePath = "";

            if (Objects.nonNull(campsiteRequest.getFile())){
                filePath = fileService.uploadFile(campsiteRequest.getFile());
            }
            Campsite campsite = new Campsite();
            campsite.setName(campsiteRequest.getName());
            campsite.setAddress(campsiteRequest.getAddress());
            campsite.setProvince(campsiteRequest.getProvince());
            campsite.setPrice(campsiteRequest.getPrice());
            campsite.setLikeCount(0);
            campsite.setFile(filePath);
            return campsiteRepository.save(campsite);
        }catch (Exception e){
            throw new RuntimeException("Failed to save camp: "+ e.getMessage());
        }
    }

    public Campsite update(String id, CampsiteRequest campsiteRequest){
        try {
            Campsite existingCamp = findById(id);
            String filePath = "";

            if (Objects.nonNull(campsiteRequest.getFile())){
                filePath = fileService.uploadFile(campsiteRequest.getFile());
            }
            existingCamp.setName(campsiteRequest.getName());
            existingCamp.setAddress(campsiteRequest.getAddress());
            existingCamp.setProvince(campsiteRequest.getProvince());
            existingCamp.setPrice(campsiteRequest.getPrice());
            existingCamp.setFile(filePath);


            return campsiteRepository.save(existingCamp);
        }catch (Exception e){
            throw new RuntimeException("Failed to update camp: "+e.getMessage());
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

    public Campsite addLike(String id){
        try{
            Campsite existingCampsite = campsiteRepository.findById(id).orElseThrow(() -> new RuntimeException("Campsite with id: "+id+" Does Not Exists"));
            int currentLikes = existingCampsite.getLikeCount();
            existingCampsite.setLikeCount(currentLikes + 1);
            return campsiteRepository.save(existingCampsite);
        }catch (Exception e){
            throw new RuntimeException("Failed to add like: "+ e.getMessage());
        }
    }

    public Campsite removeLike(String id){
        try{
            Campsite existingCampsite = campsiteRepository.findById(id).orElseThrow(() -> new RuntimeException("Campsite with id: "+id+" Does Not Exists"));
            int currentLikes = existingCampsite.getLikeCount();
            existingCampsite.setLikeCount(currentLikes - 1);
            return campsiteRepository.save(existingCampsite);
        }catch (Exception e){
            throw new RuntimeException("Failed to remove like: "+ e.getMessage());
        }
    }

}

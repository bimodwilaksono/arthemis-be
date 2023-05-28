package com.enigma.repository;

import com.enigma.model.Campsite;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;


import java.util.List;

@Repository
public interface CampsiteRepository extends JpaRepository<Campsite, String> {
    Page<Campsite> findByProvinceIgnoreCase(String province, Pageable pageable);

}

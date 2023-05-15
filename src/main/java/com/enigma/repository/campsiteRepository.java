package com.enigma.repository;

import com.enigma.model.Campsite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface campsiteRepository extends JpaRepository<Campsite, Integer> {
}

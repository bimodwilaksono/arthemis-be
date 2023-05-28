package com.enigma.repository;

import com.enigma.model.User;
import com.enigma.utils.constants.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailIgnoreCase(String email);

    Page<User> findByRole(Role role, Pageable pageable);

    User findByEmail(String userEmail);
}

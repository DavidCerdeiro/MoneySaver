package com.TFG.app.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.TFG.app.backend.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Custom query to find a user by email
    Optional<User> findByEmail(String email);

    // Custom query to check if a user exists by email
    boolean existsByEmail(String email);

    void deleteByEmail(String email);
    
}

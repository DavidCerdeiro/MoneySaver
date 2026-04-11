package com.TFG.app.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import com.TFG.app.backend.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Custom query to find a user by email
    Optional<User> findByEmail(String email);

    // Custom query to check if a user exists by email
    boolean existsByEmail(String email);

    void deleteByEmail(String email);
    
    @Modifying
    @Query(value = "DELETE FROM \"user\" WHERE \"Email\" LIKE 'demo_%@demo.com' AND \"CreatedAt\" < NOW() - INTERVAL '24 hours'", nativeQuery = true)
    void deleteOldDemoUsers();
}

package com.TFG.app.backend.street.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TFG.app.backend.street.entity.Street;

public interface StreetRepository extends JpaRepository<Street, Integer> {
    
    
}

package com.TFG.app.backend.region.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.region.entity.Region;
import java.util.Optional;
public interface RegionRepository extends JpaRepository<Region, Integer> {


    Optional<Region> findByCountryAndCity(String country, String city);
}

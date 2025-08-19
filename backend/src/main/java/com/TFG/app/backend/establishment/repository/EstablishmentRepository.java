package com.TFG.app.backend.establishment.repository;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.region.entity.Region;

import org.springframework.data.jpa.repository.JpaRepository;
public interface EstablishmentRepository extends JpaRepository<Establishment, Integer> {

    Establishment findByNameAndRegion(String name, Region region);

    Establishment findById(int id);
}
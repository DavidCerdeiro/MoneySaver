package com.TFG.app.backend.establishment.repository;

import com.TFG.app.backend.establishment.entity.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EstablishmentRepository extends JpaRepository<Establishment, Integer> {

    Establishment findById(int id);

    List<Establishment> findAll();
}
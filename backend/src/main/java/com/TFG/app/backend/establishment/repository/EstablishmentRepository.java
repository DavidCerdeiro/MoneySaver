package com.TFG.app.backend.establishment.repository;

import com.TFG.app.backend.establishment.entity.Establishment;

import org.springframework.data.jpa.repository.JpaRepository;
public interface EstablishmentRepository extends JpaRepository<Establishment, Integer> {

    Establishment findByName(String name);

    Establishment findById(int id);
}
package com.TFG.app.backend.type_periodic.repository;

import com.TFG.app.backend.type_periodic.entity.Type_Periodic;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Type_PeriodicRepository extends JpaRepository<Type_Periodic, Integer> {

    Optional<Type_Periodic> findByName(String name);
}
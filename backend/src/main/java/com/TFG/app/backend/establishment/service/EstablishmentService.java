package com.TFG.app.backend.establishment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.establishment.repository.EstablishmentRepository;
import com.TFG.app.backend.establishment.entity.Establishment;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    public Establishment newEstablishment(String name) {
        Establishment establishment = new Establishment();
        establishment.setName(name);

        return establishmentRepository.save(establishment);
    }

    public List<Establishment> findAll() {
        return establishmentRepository.findAll();
    }

    public Establishment findById(int id) {
        return establishmentRepository.findById(id);
    }

    public Establishment findByName(String name) {
        return establishmentRepository.findByName(name);
    }
}
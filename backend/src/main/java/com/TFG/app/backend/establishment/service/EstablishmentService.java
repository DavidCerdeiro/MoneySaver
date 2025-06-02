package com.TFG.app.backend.establishment.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.establishment.repository.EstablishmentRepository;
import com.TFG.app.backend.establishment.entity.Establishment;


@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    public Establishment createEstablishment(Establishment establishment) {
        return establishmentRepository.save(establishment);
    }

}
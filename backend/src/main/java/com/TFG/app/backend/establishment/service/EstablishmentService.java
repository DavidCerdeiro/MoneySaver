package com.TFG.app.backend.establishment.service;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.repository.EstablishmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

}
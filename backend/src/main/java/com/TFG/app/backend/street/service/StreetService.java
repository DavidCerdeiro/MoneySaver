package com.TFG.app.backend.street.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.street.repository.StreetRepository;
import com.TFG.app.backend.street.entity.Street;

@Service
public class StreetService {
    private final StreetRepository streetRepository;

    public StreetService(StreetRepository streetRepository) {
        this.streetRepository = streetRepository;
    }

    public Street createStreet(Street street) {
        return streetRepository.save(street);
    }
}

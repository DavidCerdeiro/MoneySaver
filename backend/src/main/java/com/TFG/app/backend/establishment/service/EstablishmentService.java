package com.TFG.app.backend.establishment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.establishment.repository.EstablishmentRepository;
import com.TFG.app.backend.region.service.RegionService;
import com.TFG.app.backend.establishment.entity.Establishment;
import java.util.Optional;
import com.TFG.app.backend.region.entity.Region;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;
    private final RegionService regionService;

    public EstablishmentService(EstablishmentRepository establishmentRepository, RegionService regionService) {
        this.establishmentRepository = establishmentRepository;
        this.regionService = regionService;
    }

    public Establishment newEstablishment(String name, String country, String city) {
        Establishment establishment = new Establishment();
        establishment.setName(name);
        Optional<Region> region = regionService.getRegionByCountryAndCity(country, city);
        if (region.isPresent()) {
            establishment.setRegion(region.get());
        }else{
            establishment.setRegion(regionService.createRegion(country, city));
        }
        return establishmentRepository.save(establishment);
    }

    public List<Establishment> findAll() {
        return establishmentRepository.findAll();
    }

    public Establishment findById(int id) {
        return establishmentRepository.findById(id);
    }

}
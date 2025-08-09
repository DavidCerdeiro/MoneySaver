package com.TFG.app.backend.region.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.region.entity.Region;
import com.TFG.app.backend.region.repository.RegionRepository;
import java.util.Optional;
@Service
public class RegionService {
    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }   

    public Optional<Region> getRegionByCountryAndCity(String country, String city) {
        return regionRepository.findByCountryAndCity(country, city);
    }

    public Region createRegion(String country, String city) {
        Region region = new Region();
        region.setCountry(country);
        region.setCity(city);
        return regionRepository.save(region);
    }
}

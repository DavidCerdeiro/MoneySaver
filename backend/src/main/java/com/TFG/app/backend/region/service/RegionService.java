package com.TFG.app.backend.region.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.region.entity.Region;
import com.TFG.app.backend.region.repository.RegionRepository;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }   

    public Region createRegion(Region region) {
        return regionRepository.save(region);
    }
}

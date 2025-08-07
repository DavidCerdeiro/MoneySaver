package com.TFG.app.backend.region.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.region.entity.Region;
import com.TFG.app.backend.region.service.RegionService;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping("/create")
    public Region create(@RequestBody Region region) {
        return regionService.createRegion(region);
    }
}

package com.TFG.app.backend.region.controller;

import org.springframework.web.bind.annotation.*;


import com.TFG.app.backend.region.service.RegionService;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @SuppressWarnings("unused")
    private final RegionService regionService;
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

}

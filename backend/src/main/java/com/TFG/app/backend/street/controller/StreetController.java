package com.TFG.app.backend.street.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.street.entity.Street;
import com.TFG.app.backend.street.service.StreetService;

@RestController
@RequestMapping("/api/streets")
public class StreetController {
    private final StreetService streetService;

    public StreetController(StreetService streetService) {
        this.streetService = streetService;
    }

    @PostMapping("/create")
    public  Street create(@RequestBody Street street) {
        return streetService.createStreet(street);
    }
}

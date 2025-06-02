package com.TFG.app.backend.street.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TFG.app.backend.street.entity.Street;
import com.TFG.app.backend.street.service.StreetService;

@RestController
@RequestMapping("/api/streets")
public class StreetController {
    private final StreetService streetService;

    public StreetController(StreetService streetService) {
        this.streetService = streetService;
    }

    @PostMapping
    public  Street create(@RequestBody Street street) {
        return streetService.createStreet(street);
    }
}

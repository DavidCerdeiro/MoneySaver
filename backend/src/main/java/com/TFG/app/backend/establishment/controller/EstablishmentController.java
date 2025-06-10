package com.TFG.app.backend.establishment.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.service.EstablishmentService;

@RestController
@RequestMapping("/api/establishments")
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @PostMapping("create")
    public Establishment create(@RequestBody Establishment establishment) {
        return establishmentService.createEstablishment(establishment);
    }
}
